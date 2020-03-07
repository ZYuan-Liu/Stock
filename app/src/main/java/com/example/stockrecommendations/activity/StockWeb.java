package com.example.stockrecommendations.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.stockrecommendations.R;
import com.example.stockrecommendations.api.RetrofitApi;
import com.example.stockrecommendations.base.BaseTitleActivity;
import com.example.stockrecommendations.entity.AddMyStockBean;
import com.example.stockrecommendations.util.Constants;
import com.example.stockrecommendations.util.NoAdWebViewClient;
import com.example.stockrecommendations.util.NoDoubleClickListener;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class StockWeb extends BaseTitleActivity {
    private String mUrl = "http://quote.eastmoney.com/";
    private String code;
    private String name;
    private WebView webView;
    private WebSettings settings;
    private LinearLayout upLoad;
    private SVProgressHUD svProgressHUD;

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
        setContentView(R.layout.activity_stock_web);
    }

    @Override
    public void initView() {
        super.initView();
        svProgressHUD = new SVProgressHUD(this);
        setTop();
        svProgressHUD.showWithStatus("加载中");
        upLoad = findViewById(R.id.uploadInfo);
        webView = findViewById(R.id.wb);
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        webView.setWebViewClient(new NoAdWebViewClient(this, webView, getWindow().getDecorView(),svProgressHUD));
        //加载需要显示的网页
        code = getIntent().getExtras().getString("code");
        name = getIntent().getExtras().getString("name");
        if (getIntent().getExtras().getString("zixuan")!=null){
            upLoad.setVisibility(View.GONE);
        }else {
            upLoad.setVisibility(View.VISIBLE);
        }
        setTitle(name + "\n" + code);
        if (code != null)
            if (code.subSequence(0, 1).equals("6")) {
                mUrl = " http://emwap.eastmoney.com/quota/stock/index/" + code + "1";
            } else {
                mUrl = " http://emwap.eastmoney.com/quota/stock/index/" + code + "2";
            }

        Log.e("url--", mUrl);
        webView.loadUrl(mUrl);
        webView.setVerticalScrollBarEnabled(false);
    }

    @Override
    public void setListener() {
        upLoad.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                svProgressHUD.showWithStatus("添加中");
                addStock(getIntent().getExtras().getString("code"));
            }
        });
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness(Context mContext) {

    }


    public void addStock(String code) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi service = retrofit.create(RetrofitApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", Constants.userId);
        params.put("code", code);
        Call<AddMyStockBean> call = service.addMyStock(params);
        call.enqueue(new Callback<AddMyStockBean>() {
            @Override
            public void onResponse(Call<AddMyStockBean> call, Response<AddMyStockBean> response) {
                svProgressHUD.dismiss();
                if (response.body() != null) {
                    if (response.body().getStateCode().equals("10000")) {
                        showToast("添加成功");
                    } else {
                        showToast(response.body().getErrMsg());
                    }
                } else {
                    showToast("添加失败");
                }

            }


            @Override
            public void onFailure(Call<AddMyStockBean> call, Throwable t) {
                showToast("添加失败");
                Log.e("错误信息-", t.toString());
                svProgressHUD.dismiss();
            }
        });
    }


    @Override
//设置回退
//覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        } else {
            finish();
        }
        return false;
    }
}
