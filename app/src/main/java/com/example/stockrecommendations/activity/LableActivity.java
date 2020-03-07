package com.example.stockrecommendations.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockrecommendations.R;
import com.example.stockrecommendations.adapter.LinkLabelAdapter;
import com.example.stockrecommendations.base.BaseTitleActivity;
import com.example.stockrecommendations.entity.Event;
import com.example.stockrecommendations.entity.LinkLabelBean;
import com.example.stockrecommendations.util.EventBusUtil;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LableActivity extends BaseTitleActivity {


    private RecyclerView recyclerView;
    private TextView btn;
    private List<LinkLabelBean> list = new ArrayList<>();
    private List<String> list1 = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();
    private List<String> list3 = new ArrayList<>();
    private List<String> list4 = new ArrayList<>();
    private Gson gson = new Gson();

    private String[] str = new String[]{"标题1", "格雷厄姆", "巴菲特", "财务回归", "财务决策", "文本决策",
            "标题2", "大型企业", "中型企业", "小型企业", "微型企业", "无法判别",
            "标题3", "安防设备", "包装材料", "保险", "玻璃陶瓷", "材料行业", "船舶制造", "电力行业", "电信运营", "电子信息", "电子元件", "多元金融",
            "房地产", "纺织服装", "钢铁行业", "港口水运", "高速公路", "工程建设", "工艺商品", "公用事业", "贵金属", "国际贸易",
            "航天航空", "化肥行业", "化工行业", "化纤行业", "环保工程", "机械行业", "家电行业", "交运设备", "交运物流", "金属制品",
            "旅游酒店", "煤炭采选", "民航机场", "木业家具", "酿酒行业", "农牧饲渔", "农药兽药", "汽车行业", "券商信托", "软件服务",
            "商业百货", "石油行业", "食品饮料", "输配电气", "水泥建材", "塑胶制品", "通讯行业", "文化传媒", "文教休闲", "医疗行业",
            "医药制造", "仪器仪表", "银行", "有色金属", "园林工程", "造纸印刷", "珠宝首饰", "专用设备", "装修装饰", "综合行业",
            "标题4", "安徽板块", "北京板块", "福建板块", "甘肃板块", "广东板块", "广西板块", "贵州板块", "海南板块", "河北板块", "河南板块",
            "黑龙江", "湖北板块", "湖南板块", "吉林板块", "江苏板块", "江西板块", "辽宁板块", "内蒙古", "宁夏板块", "青海板块", "山东板块", "山西板块",
            "陕西板块", "上海板块", "四川板块", "天津板块", "西藏板块", "新疆板块", "云南板块", "浙江板块", "重庆板块"};
    private Map<String, List<String>> hashMap = new HashMap<>();
    private Map<String, List<Map>> hashMapLables = new HashMap<>();
    private Map<String, Object> hashMapQuery = new HashMap<>();


    private LinkLabelBean linkLabelBean;


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindLayout() {
        setContentView(R.layout.activity_lable);
    }

    @Override
    public void initView() {
        super.initView();
        setTop();
        setTitle("标签选择");
        initData();
        recyclerView = findViewById(R.id.recyclerview);
        btn = findViewById(R.id.notarize_button);
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = recyclerView.getAdapter().getItemViewType(position);//获得返回值spanCount/spanSize=每行item数（每行四个）
                if (type == 99) {
                    return mLayoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(mLayoutManager);
        LinkLabelAdapter linkLabelAdapter = new LinkLabelAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(linkLabelAdapter);


    }

    private void initData() {
        for (int i = 0; i < str.length; i++) {
            linkLabelBean = new LinkLabelBean();
            linkLabelBean.setStr(str[i]);
            list.add(linkLabelBean);
        }
    }

    @Override
    public void setListener() {
        btn.setOnClickListener(this);


    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    protected void onResume() {
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

            case R.id.notarize_button:
                //这边可以优化。
                boolean zhishao = false;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelect()) {
                        zhishao = true;
                    }
                }
                if (!zhishao) {
                    showToast("请至少选择一个标签");
                    return;
                }
                hashMap.clear();
                list1.clear();
                list2.clear();
                list3.clear();
                list4.clear();

                List<Map<String, String>> listLable = new ArrayList<Map<String, String>>();

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelect()) {
                        if (i > 0 && i <= 5) {
                            list1.add(list.get(i).getStr());
                        } else if (i > 5 && i <= 11) {
                            list2.add(list.get(i).getStr());
                        } else if (i > 11 && i <= 73) {
                            list3.add(list.get(i).getStr());
                        } else {
                            list4.add(list.get(i).getStr());
                        }
                    }
                }

                if (list1.size() > 0) {
                    for (int i = 0; i < list1.size(); i++) {  //不知道为啥hashmap1放外面声明然后用.clear清除之后会导致.add(map)取不到值，只能循环的时候新创建一个对象了
                        Map<String, String> hashMap1 = new HashMap<>(); //以后有时间可以优化0.0
                        hashMap1.put("supLab", "sl-type");
                        hashMap1.put("subLab", list1.get(i));
                        listLable.add(hashMap1);
                    }
                }
                if (list2.size() > 0) {
                    for (int i = 0; i < list2.size(); i++) {
                        Map<String, String> hashMap1 = new HashMap<>();
                        hashMap1.put("supLab", "gsgm");
                        hashMap1.put("subLab", list2.get(i));
                        listLable.add(hashMap1);
                    }
                }
                if (list3.size() > 0) {
                    for (int i = 0; i < list3.size(); i++) {
                        Map<String, String> hashMap1 = new HashMap<>();
                        hashMap1.put("supLab", "hybk");
                        hashMap1.put("subLab", list3.get(i));
                        listLable.add(hashMap1);
                    }
                }
                if (list4.size() > 0) {

                    for (int i = 0; i < list4.size(); i++) {
                        Map<String, String> hashMap1 = new HashMap<>();
                        hashMap1.put("supLab", "dqbk");
                        hashMap1.put("subLab", list4.get(i));
                        listLable.add(hashMap1);
                    }
                }
                Log.e("Listlables数据------", listLable.get(0).toString());
                hashMapQuery.put("currPage", "1");
                hashMapQuery.put("labels", listLable);

                String s = gson.toJson(hashMapQuery);
                Log.e("选中数据------", s);

                Event<String> event = new Event<>(1, s);
                EventBusUtil.sendEvent(event);
                finish();
                break;
            default:
                break;

        }
    }
}
