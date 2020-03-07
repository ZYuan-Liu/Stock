package com.example.stockrecommendations.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.example.stockrecommendations.R;
import com.example.stockrecommendations.base.BaseTitleActivity;

public class EasterEggActivity extends BaseTitleActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void bindLayout() {
        setContentView(R.layout.activity_eastegg);
    }

    @Override
    public void initView() {
        super.initView();
        setTop();
        setTitle("这个难道是彩蛋？！");

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


}
