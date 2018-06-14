package com.pull.recycleyview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.pull.recycleyview.library.AutoLoad.AutoLoadRecyclerView;
import com.pull.recycleyview.library.Divider.BaseItemDecoration;
import com.pull.recycleyview.library.HeaderAndFooter.OnItemClickListener;
import com.pull.recycleyview.library.HeaderAndFooter.OnItemLongClickListener;
import com.pull.recycleyview.library.LayoutManager.PTLGridLayoutManager;
import com.pull.recycleyview.library.PullToLoad.OnLoadListener;
import com.pull.recycleyview.library.PullToRefresh.OnRefreshListener;
import com.pull.recycleyview.library.SimpleAdapter.SimpleAdapter;
import com.pull.recycleyview.library.SimpleAdapter.ViewHolder;

import java.util.ArrayList;

public class AutoLoadActivity extends AppCompatActivity{

    public static void actionStart(Context context) {
        Intent intent = new Intent(context,AutoLoadActivity.class);
        context.startActivity(intent);
    }


    private AutoLoadRecyclerView rcv;
    private ArrayList<String> imgs;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_load);
        imgs = ImgDataUtil.getImgDatas();
        handler = new Handler();

        rcv = (AutoLoadRecyclerView) findViewById(R.id.rcv);

        rcv.setLayoutManager(new PTLGridLayoutManager(2, PTLGridLayoutManager.VERTICAL,false));

//        设置适配器，封装后的适配器只需要实现一个函数
        rcv.setAdapter(new SimpleAdapter<String>(this, imgs, R.layout.item_test) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, String data) {
                Glide.with(mContext).load(data).into(holder.<ImageView>getView(R.id.iv));
            }
        });
//        设置刷新监听
        rcv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onStartRefreshing() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgs.clear();
                        imgs.addAll(ImgDataUtil.getImgDatas());
                        rcv.completeRefresh();
                        rcv.setNoMore(false);
                    }
                }, 1000);
            }
        });
//        设置加载监听
        rcv.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onStartLoading(int skip) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> newImages = ImgDataUtil.getImgDatas();
                        imgs.addAll(newImages);
                        rcv.completeLoad(newImages.size());
                        if (imgs.size() > 20) {
                            rcv.setNoMore(true);
                        }
                    }
                }, 1000);
            }
        });
//        设置分割线
        rcv.addItemDecoration(new BaseItemDecoration(this, R.color.colorAccent));

        rcv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(AutoLoadActivity.this, "item" + position + " has been clicked", Toast.LENGTH_SHORT).show();
            }
        });
        rcv.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                Toast.makeText(AutoLoadActivity.this, "item" + position + " has been long clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_header:
                rcv.addHeaderView(getHeaderView());
                break;
            case R.id.btn_remove_header:
                if (headerViews.size()==0) return;
                rcv.removeHeaderView(headerViews.get(headerViews.size()-1));
                headerViews.remove(headerViews.size()-1);
                break;
            case R.id.btn_add_footer:
                rcv.addFooterView(getFooterView());
                break;
            case R.id.btn_remove_footer:
                if (footerViews.size()==0) return;
                rcv.removeFooterView(footerViews.get(footerViews.size() - 1));
                footerViews.remove(footerViews.size() - 1);
                break;
        }
    }

    private ArrayList<View> headerViews = new ArrayList<>();
    private ArrayList<View> footerViews = new ArrayList<>();

    private View getHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.item_header,rcv,false);
        ((TextView) view.findViewById(R.id.tv)).setText("Header"+headerViews.size());
        headerViews.add(view);
        return view;
    }

    private View getFooterView() {
        View view = getLayoutInflater().inflate(R.layout.item_footer,rcv,false);
        ((TextView) view.findViewById(R.id.tv)).setText("Header"+footerViews.size());
        footerViews.add(view);
        return view;
    }
}
