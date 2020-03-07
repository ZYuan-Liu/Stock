package com.example.stockrecommendations.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.stockrecommendations.R;
import com.example.stockrecommendations.base.BaseTitleActivity;

public class NewsWeb extends BaseTitleActivity {
    private String mUrl = "";
    private WebView webView;
    private WebSettings settings;

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
        setContentView(R.layout.activity_news_web);
    }

    @Override
    public void initView() {
        super.initView();
        setTop();
        setTitle("资讯详情");
        webView = findViewById(R.id.wb);
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(false);
        settings.setAppCacheEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        //加载需要显示的网页
        mUrl = getIntent().getExtras().getString("url");
        webView.loadUrl(mUrl);
        webView.setVerticalScrollBarEnabled(false);
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
