package com.pull.recycleyview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_linear_manager:
                LinearManagerActivity.actionStart(this);
                break;
            case R.id.btn_grid_manager:
                GridManagerActivity.actionStart(this);
                break;
            case R.id.btn_staggred_grid_manager:
                StaggredGridManagerActivity.actionStart(this);
                break;
            case R.id.btn_auto_load:
                AutoLoadActivity.actionStart(this);
                break;
            case R.id.btn_refresh_load:
                RefreshAndLoadActivity.actionStart(this);
                break;
        }
    }
}
