package com.example.stockrecommendations.activity;

import android.os.Handler;
import android.view.WindowManager;
import com.example.stockrecommendations.R;
import com.example.stockrecommendations.base.BaseTitleActivity;


/**
 * Created by lzy123 on 2018/9/4.
 */

public class SplashActivity extends BaseTitleActivity {
    //延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 3000;

    @Override
    public void bindLayout() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                goHome();
            }
        }, SPLASH_DELAY_MILLIS);

    }
    private void goHome() {
        startActivity(LoginActivity.class);
        finish();
    }
}
