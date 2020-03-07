package com.example.stockrecommendations.util;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;


import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.stockrecommendations.R;

/**
 * Created by lzy123 on 2019/08/05.
 */
public class NoAdWebViewClient extends WebViewClient {
    private Context context;
    private WebView webView;
    private LinearLayout linearLayout;
    private View view;
    private SVProgressHUD svProgressHUD;

    public NoAdWebViewClient(Context context, WebView webView, View view, SVProgressHUD svProgressHUD) {
        this.context = context;
        this.webView = webView;
        this.view = view;
        this.svProgressHUD = svProgressHUD;
        linearLayout = view.findViewById(R.id.ll_zhegai);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        view.loadUrl("javascript:function setTop(){document.querySelector('.stock-footer').style.display=\"none\";}setTop();");
        view.loadUrl("javascript:function setTop(){document.querySelector('#adDTT').style.display=\"none\";}setTop();");
        view.loadUrl("javascript:function setTop(){document.querySelector('.comm-nav').style.display=\"none\";}setTop();");
        view.loadUrl("javascript:function setTop(){document.querySelector('#stock-infos-list > li:nth-child(2) > div:nth-child(2)').style.display=\"none\";}setTop();");
        view.loadUrl("javascript:function setTop(){document.querySelector('.comm-footer').style.display=\"none\";}setTop();");
        view.loadUrl("javascript:function setTop(){document.querySelector('.stock-detail > div:nth-child(7)').style.display=\"none\";}setTop();");
        linearLayout.setVisibility(View.GONE);
        svProgressHUD.dismiss();
    }


}