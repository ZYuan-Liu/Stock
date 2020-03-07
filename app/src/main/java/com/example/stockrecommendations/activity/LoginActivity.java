package com.example.stockrecommendations.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.stockrecommendations.R;
import com.example.stockrecommendations.api.RetrofitApi;
import com.example.stockrecommendations.base.BaseTitleActivity;
import com.example.stockrecommendations.entity.Event;
import com.example.stockrecommendations.entity.LoginBean;
import com.example.stockrecommendations.util.Constants;
import com.example.stockrecommendations.util.CustomVideoView;
import com.example.stockrecommendations.util.NoDoubleClickListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseTitleActivity {
    private TextView login;
    private TextView tv_forget;
    private TextView register;
    private EditText et_username, et_pwd;
    private SharedPreferences sp;
    private String userPwd, userName;
    private SVProgressHUD mSVProgressHUD;
    private CustomVideoView customVideoView;
    private long exitTime = 0;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        super.initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        );//防止软键盘弹出挤压布局
        setTop();
        customVideoView = findViewById(R.id.videoview);
        customVideoView.playVideo(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background));
        mSVProgressHUD = new SVProgressHUD(this);
        et_username = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_password);
        register = (TextView) findViewById(R.id.tl_register);
        tv_forget = (TextView) findViewById(R.id.tv_forget_pwd);
        login = (TextView) findViewById(R.id.login);
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        et_username.setText(sp.getString("name", ""));
        et_pwd.setText(sp.getString("pwd", ""));
        getFocus(et_pwd);
        getFocus(et_username);
        if (sp.getBoolean("isAuto", false)) {
            //设置默认是记录密码状态
            Editable text = et_username.getText();

            if (text != null) {
                Selection.setSelection(text, text.length());
                requestLogin();
            }
        }

    }

    public void getFocus(EditText editText) {   //透明状态栏的edtext焦点获取似乎有问题不这样弹不出软键盘
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setCursorVisible(true);
        editText.requestFocus();
    }

    @Override
    public void setListener() {
        tv_forget.setOnClickListener(this);
        register.setOnClickListener(this);


        login.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (et_pwd.getText().toString().trim().equals("") || et_username.getText().toString().trim().equals("")) {
                    showToast("请输入账号密码");
                } else {
                    requestLogin();
                }
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    protected void onResume() {
        customVideoView.setBackground(getResources().getDrawable(R.drawable.login_background));
        customVideoView.playVideo(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background));
        super.onResume();

    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        // 接受到Event后的相关逻辑

    }


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget_pwd:
                //   startActivity(ForgetPasswordActivity.class);
                break;
            case R.id.tl_register:
                //   startActivity(RegisterActivity.class);
                break;

            default:
                break;

        }
    }


    private void requestLogin() {
        userName = et_username.getText().toString().trim();
        userPwd = et_pwd.getText().toString().trim();
        mSVProgressHUD.showWithStatus("登录中");
        if (TextUtils.isEmpty(userName)) {
            showToast("请输入用户名");
            mSVProgressHUD.dismiss();
            return;
        }
        if (TextUtils.isEmpty(userPwd)) {
            showToast("请输入密码");
            mSVProgressHUD.dismiss();
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi service = retrofit.create(RetrofitApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("password", userPwd);
        Call<LoginBean> call = service.toLogin(params);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                mSVProgressHUD.dismiss();
                if (response.body() != null) {
                    if (response.body().getStateCode() == 1) {
                        SharedPreferences.Editor editor = sp.edit();  //利用edit()方法获取Editor对象, Editor可用于SharedPreferences数据的添加，删除，修改和查询。
                        editor.putString("name", et_username.getText().toString());
                        editor.putString("pwd", et_pwd.getText().toString());
                        editor.apply();
                        Constants.userId = String.valueOf(response.body().getUser_id());
                        startActivity(HomeActivity.class);
                        finish();
                    } else {
                        mSVProgressHUD.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                mSVProgressHUD.dismiss();
                Log.e("Failure",t.toString());
            }
        });

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
