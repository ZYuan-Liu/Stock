package com.example.stockrecommendations.activity;


import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.stockrecommendations.R;
import com.example.stockrecommendations.base.BaseActivity;
import com.example.stockrecommendations.fragment.MyFragment;
import com.example.stockrecommendations.fragment.MyStockFragment;
import com.example.stockrecommendations.fragment.NewsFragment;
import com.example.stockrecommendations.fragment.SelectionFragment;
import com.example.stockrecommendations.util.BottomBar;

public class HomeActivity extends BaseActivity {
    BottomBar bottomBar;
    private long exitTime = 0;

    @Override
    public void bindLayout() {
        setContentView(R.layout.activity_home);
    }

    @Override
    public void initView() {
        bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#2c2c2c", "#d81e06")
                .addItem(NewsFragment.class,
                        "资讯",
                        R.drawable.news1,
                        R.drawable.news2)
                .addItem(SelectionFragment.class,
                        "选股器",
                        R.drawable.selection1,
                        R.drawable.selection2)
                .addItem(MyStockFragment.class,
                        "自选股",
                        R.drawable.mystock1,
                        R.drawable.mystock2)
                .addItem(MyFragment.class,
                        "个人",
                        R.drawable.mine1,
                        R.drawable.mine2)
                .build();

    }


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }


    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}