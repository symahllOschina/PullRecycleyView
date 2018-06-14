package com.pull.recycleyview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duanc.refreshlayout.SwipyRefreshLayout;
import com.duanc.refreshlayout.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

public class RefreshAndLoadActivity extends AppCompatActivity{

    public static void actionStart(Context context) {
        Intent intent = new Intent(context,RefreshAndLoadActivity.class);
        context.startActivity(intent);
    }

    private RecyclerView.LayoutManager mLayoutManager;
    List<String> mDatas;
    MyAdapter myAdapter;


    private com.duanc.refreshlayout.SwipyRefreshLayout swipyRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_load_activity);
        swipyRefreshLayout = findViewById(R.id.swipyRefreshLayout);//初始化布局管理器（RecyclerView为线性垂直方式展示列表）
        recyclerView = findViewById(R.id.recyclerView);

        initData();
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                //下拉刷新
                if(direction == SwipyRefreshLayoutDirection.TOP){

                    swipyRefreshLayout.setRefreshing(false);


                }else if(direction == SwipyRefreshLayoutDirection.BOTTOM){

                    swipyRefreshLayout.setRefreshing(false);

                }
            }
        });
    }
    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            mDatas.add("TEXT" + i);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder;
            View view = LayoutInflater.from(RefreshAndLoadActivity.this).inflate(R.layout.refresh_load_item, parent, false);
            holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv);
            }
        }
    }
}
