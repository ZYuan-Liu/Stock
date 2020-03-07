package com.example.stockrecommendations.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.stockrecommendations.R;
import com.example.stockrecommendations.util.AndroidWorkaround;


/**
 * Created by lzy123 on 2017/7/19.
 */

public class BaseTitleActivity extends BaseActivity {
    protected TextView generalTitleLabel;//标题
    protected LinearLayout generalTitleBackImg;
    private ImageView iv_back;//返回键
    private RelativeLayout title_bg_color;//标题栏

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void bindLayout() {

    }

    public void setTop() {
        View v = (View) findViewById(R.id.content_home_exper);
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.height = getHeight();//lp.height=LayoutParams.WRAP_CONTENT;
        v.setLayoutParams(lp);
    }

    @Override
    public void initView() {
        AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        generalTitleLabel = findViewById(R.id.generalTitleLabel);
        generalTitleBackImg = findViewById(R.id.generalTitleBackImg);
        iv_back = findViewById(R.id.iv_back);
        title_bg_color = findViewById(R.id.title_bg_color);
        if (generalTitleBackImg == null) return;
        generalTitleBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public void setListener() {
//        if (generalTitleLabelTwo == null) return;
//        generalTitleLabelTwo.setOnClickListener(this);
        if (generalTitleLabel == null) return;
        generalTitleLabel.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
//        switch(v.getId()){
//            case R.id.generalTitleLabel:
//                Log.i("test","-----点击左标题------");
//                break;
//            case R.id.generalTitleLabelTwo:
//                Log.i("test","-----点击右标题------");
//                break;
//            default:
//                break;
//        }

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    public void setTitle(String title) {

        generalTitleLabel.setText(title);
    }

    /**
     * 设置隐藏标题
     */
    public void setOrVisible(boolean ishiden) {
        if (ishiden) {
            generalTitleLabel.setVisibility(View.GONE);
        } else {
            generalTitleLabel.setVisibility(View.VISIBLE);
        }
    }

    public void hidenbackPress(boolean ishiden) {
        if (ishiden) {
            generalTitleBackImg.setVisibility(View.GONE);
        } else {
            generalTitleBackImg.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 改变titlebar颜色
     */
    public void changeTitleBar(Context mContext) {
        if (iv_back == null) return;
        if (generalTitleLabel == null) return;
        if (title_bg_color == null) return;
        //Picasso.with(mContext).load(R.drawable.backpress).into(iv_back);
        generalTitleLabel.setTextColor(getResources().getColor(R.color.textblack));
        title_bg_color.setBackgroundColor(getResources().getColor(R.color.white));
    }


}
