package com.example.stockrecommendations.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.stockrecommendations.R;
import com.example.stockrecommendations.activity.StockWeb;
import com.example.stockrecommendations.adapter.BaseRecyclerAdapter;
import com.example.stockrecommendations.adapter.SmartViewHolder;
import com.example.stockrecommendations.api.RetrofitApi;
import com.example.stockrecommendations.base.BaseFragment;
import com.example.stockrecommendations.entity.DeleteStockBean;
import com.example.stockrecommendations.entity.Event;
import com.example.stockrecommendations.entity.MyStockListBean;
import com.example.stockrecommendations.entity.SegmentBean;
import com.example.stockrecommendations.util.Constants;
import com.example.stockrecommendations.util.EventBusUtil;
import com.example.stockrecommendations.util.NoDoubleClickListener;
import com.example.stockrecommendations.util.NoteDialogEx;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyStockFragment extends BaseFragment {
    private View view;
    private RecyclerView recyview;
    private EditText searchEdText;
    private ImageView searchView;
    private int pageNum = 1;
    private List<MyStockListBean.ResultBean> list1 = new ArrayList<MyStockListBean.ResultBean>();
    private BaseRecyclerAdapter<MyStockListBean.ResultBean> mAdapter;
    private RefreshLayout refreshLayout;
    private static HashSet<String> stockIds = new HashSet();
    private final static String shIndex = "sh000001";
    private final static String szIndex = "sz399001";
    private final static String cybIndex = "sz399006";   //三大指数的代码
    private SVProgressHUD mSVProgressHUD;
    private String deleteCodes = "";//要删除的自选股

    public class Stock {
        public String id, name;
        public String open_price, yesterday_price, now_price, high_price, low_price, volume_of_trade, volume_of_money;
    }//定义股票类，封装对应新浪接口的股票数据

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mystock, container, false);
        EventBus.getDefault().register(this);
        stockIds.add("sh000001");
        stockIds.add("sz399001");
        stockIds.add("sz399006");//三大指数
        mSVProgressHUD = new SVProgressHUD(getActivity());
        initView();
        initData();
        return view;
    }

    public void initView() {
        setTop(view);
        searchEdText = view.findViewById(R.id.ed_text);
        searchView = view.findViewById(R.id.img_search);
        searchView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String stockId;
                if (searchEdText.getText().length() == 6) {
                    if (searchEdText.getText().toString().subSequence(0, 1).equals("6")) {
                        stockId = "sh" + searchEdText.getText().toString();
                    } else {
                        stockId = "sz" + searchEdText.getText().toString();
                    }
                    searchStock(stockId);
                } else {
                    showShortToast("请输入正确的股票代码");
                }
            }
        });

        recyview = view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableFooterFollowWhenNoMoreData(true);
        getFocus(searchEdText);
        initMystockList();
    }

    public void getFocus(EditText editText) {   //透明状态栏的edtext焦点获取似乎有问题不这样弹不出软键盘
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setCursorVisible(true);
        editText.requestFocus();
    }

    public void initData() {
        Timer timer = new Timer("RefreshStocks");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshStocks();
                queryMyStockList(true);
            }
        }, 0, 15000);//15秒对页面进行一次刷新


    }

    private void refreshStocks() {
        String ids = "";
        for (String id : stockIds) {
            ids += id + ",";
        }
        queryStocks(ids);
    }//刷新数据

    public void searchStock(final String stockId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hq.sinajs.cn/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        RetrofitApi service = retrofit.create(RetrofitApi.class);
        Call<String> call = service.getStock(stockId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    String[] stocks = response.body().toString().replaceAll("\n", "").split(";");
                    for (String stock : stocks) {
                        String[] leftRight = stock.split("=");//拆开数据与名称
                        String right = leftRight[1].replaceAll("\"", "");
                        if (right.isEmpty()) {
                            showShortToast("请输入正确的股票代码！");
                            break;
                        }
                        String[] values = right.split(",");
                        Bundle b = new Bundle();
                        b.putString("code", stockId.substring(2));
                        b.putString("name", values[0]);
                        startActivity(StockWeb.class, b);

                    }


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    public void queryStocks(String list) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hq.sinajs.cn/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("http://push2.eastmoney.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi service = retrofit.create(RetrofitApi.class);
        Call<String> call = service.getStock(list);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    String result = response.body().toString();
                    Log.e("获取的字符串----", result);
                    updateStockListView(sinaResponseToStocks(result));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        //?pn=1&pz=3&po=1&fltt=2&fid=f3&fs=m:90+t:2&fields=f3,f14,f128,f136,f140   解析一下东方财富网页的接口参数对应的数据
        RetrofitApi service1 = retrofit1.create(RetrofitApi.class);
        Call<SegmentBean> call1 = service1.getIndusty("1", "3", "1", "2", "f3", "m:90+t:2", "f3,f14,f128,f136,f140");
        call1.enqueue(new Callback<SegmentBean>() {
            @Override
            public void onResponse(Call<SegmentBean> call, Response<SegmentBean> response) {
//                Log.e("板块----", response.body().getData().getDiff().getOne().getF14() + "涨幅--" + response.body().getData().getDiff().getOne().getF3());
                updateBkinfo(true, response.body());
            }

            @Override
            public void onFailure(Call<SegmentBean> call, Throwable t) {
                Log.e("行业板块查询崩溃", t.toString());

            }
        });

        RetrofitApi service2 = retrofit1.create(RetrofitApi.class);
        Call<SegmentBean> call2 = service2.getConcept("1", "3", "1", "2", "f3", "m:90+t:3", "f3,f14,f128,f136,f140");
        call2.enqueue(new Callback<SegmentBean>() {
            @Override
            public void onResponse(Call<SegmentBean> call, Response<SegmentBean> response) {
                updateBkinfo(false, response.body());
            }

            @Override
            public void onFailure(Call<SegmentBean> call, Throwable t) {
                Log.e("概念板块查询崩溃", t.toString());

            }
        });

    }

    public void deleteMyStock(String userId, String stockCode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi service = retrofit.create(RetrofitApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("codes", stockCode);
        Call<DeleteStockBean> call = service.toDelete(params);
        call.enqueue(new Callback<DeleteStockBean>() {
            @Override
            public void onResponse(Call<DeleteStockBean> call, Response<DeleteStockBean> response) {
                if (response.body() != null) {
                    if (response.body().getStateCode().equals("10000")) {
                        showShortToast("删除成功");
                        queryMyStockList(true);
                    } else {
                        showShortToast(response.body().getErrMsg());
                    }
                } else {
                    showShortToast("删除失败");
                }
                mSVProgressHUD.dismiss();
            }

            @Override
            public void onFailure(Call<DeleteStockBean> call, Throwable t) {
                showShortToast("网络请求失败");
                mSVProgressHUD.dismiss();
            }
        });

    }

    public void queryMyStockList(final Boolean isRefresh) {
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        if (Constants.userId != null) {
            RetrofitApi service3 = retrofit2.create(RetrofitApi.class);
            Map<String, String> params = new HashMap<>();
            params.put("user_id", Constants.userId);
            params.put("currPage", String.valueOf(pageNum));
            Call<MyStockListBean> call3 = service3.getMysStockList(params);
            call3.enqueue(new Callback<MyStockListBean>() {
                @Override
                public void onResponse(Call<MyStockListBean> call, Response<MyStockListBean> response) {
                    if (response.body() != null) {
                        list1.clear();
                        if (isRefresh) {
                            list1 = response.body().getResult();
                            mAdapter.refresh(list1);
                            refreshLayout.finishRefresh();
                            refreshLayout.resetNoMoreData();
                        } else {
                            list1.addAll(response.body().getResult());
                            mAdapter.loadMore(response.body().getResult());
                        }
                    } else {
                        if (isRefresh) {

                        } else {
                            showShortToast("没有更多自选股数据了");
                        }
                    }

                }

                @Override
                public void onFailure(Call<MyStockListBean> call, Throwable t) {


                }
            });
        }
    }


    public TreeMap<String, Stock> sinaResponseToStocks(String response) {
        response = response.replaceAll("\n", "");
        String[] stocks = response.split(";");//把多次数据拆开
        TreeMap<String, Stock> stockMap = new TreeMap();
        for (String stock : stocks) {
            String[] leftRight = stock.split("=");//拆开数据与名称
            if (leftRight.length < 2)
                continue;
            String right = leftRight[1].replaceAll("\"", "");
            if (right.isEmpty())
                continue;
            String left = leftRight[0];
            if (left.isEmpty())
                continue;
            Stock stockNow = new Stock();//对数据进行存储
            stockNow.id = left.split("_")[2];
            String[] values = right.split(",");
            stockNow.name = values[0];
            stockNow.open_price = values[1];
            stockNow.yesterday_price = values[2];
            stockNow.now_price = values[3];
            stockNow.high_price = values[4];
            stockNow.low_price = values[5];
            stockNow.volume_of_trade = values[8];
            stockNow.volume_of_money = values[9];
            stockMap.put(stockNow.id, stockNow);
        }
        return stockMap;
    }//把接口的数据进行拆分并存起来


    @SuppressLint("ResourceType")
    public void updateStockListView(TreeMap<String, Stock> stockMap) {
        Collection<Stock> stocks = stockMap.values();
        for (Stock stock : stocks) {//sh000001
            if (stock.id.equals(shIndex) || stock.id.equals(szIndex) || stock.id.equals(cybIndex)) {
                Double dNow = Double.parseDouble(stock.now_price);
                Double dYesterday = Double.parseDouble(stock.yesterday_price);
                Double dIncrease = dNow - dYesterday;
                Double dPercent = dIncrease / dYesterday * 100;
                String change = String.format("%.2f", dPercent) + "%  " + String.format("%.2f", dIncrease);
                int indexId = 0;
                int changeId = 0;
                int colorId = 0;   //后面的颜色
                switch (stock.id) {//初始化三大股指数据
                    case shIndex:
                        indexId = R.id.stock_sh_index;
                        changeId = R.id.stock_sh_change;
                        colorId = R.id.ly_szzs;
                        break;
                    case szIndex:
                        indexId = R.id.stock_sz_index;
                        changeId = R.id.stock_sz_change;
                        colorId = R.id.ly_szcz;
                        break;
                    case cybIndex:
                        indexId = R.id.stock_chuang_index;
                        changeId = R.id.stock_chuang_change;
                        colorId = R.id.ly_cybz;
                        break;
                }
                TextView indexText = (TextView) view.findViewById(indexId);
                String nowPrice = String.format("%.2f", dNow);
                indexText.setText(nowPrice);
                int color = Color.GRAY;
                if (dIncrease > 0) {
                    color = getActivity().getResources().getColor(R.color.stockred);
                } else if (dIncrease < 0) {
                    color = getActivity().getResources().getColor(R.color.stockgreen);
                }
                LinearLayout colorLayout = view.findViewById(colorId);
                colorLayout.setBackgroundColor(color);
                TextView changeText = view.findViewById(changeId);
                changeText.setText(change);
                continue;
            }

        }
    }

    public void updateBkinfo(boolean isHangye, SegmentBean segmentBean) {
        TextView bkname1, bkname2, bkname3;   //行业板块与概念板块top3的名称
        TextView bkvalue1, bkvalue2, bkvalue3;//行业板块与概念板块top3的涨幅
        TextView bkstockname1, bkstockname2, bkstockname3;//行业板块与概念板块top3股票的名称
        TextView bkstockvalue1, bkstockvalue2, bkstockvalue3;//行业板块与概念板块top3股票的涨幅
        if (isHangye) {
            bkname1 = view.findViewById(R.id.hy_top1_name);
            bkvalue1 = view.findViewById(R.id.hy_top1_value);
            bkstockname1 = view.findViewById(R.id.hy_stock_top1_name);
            bkstockvalue1 = view.findViewById(R.id.hy_stock_top1_value);

            bkname2 = view.findViewById(R.id.hy_top2_name);
            bkvalue2 = view.findViewById(R.id.hy_top2_value);
            bkstockname2 = view.findViewById(R.id.hy_stock_top2_name);
            bkstockvalue2 = view.findViewById(R.id.hy_stock_top2_value);

            bkname3 = view.findViewById(R.id.hy_top3_name);
            bkvalue3 = view.findViewById(R.id.hy_top3_value);
            bkstockname3 = view.findViewById(R.id.hy_stock_top3_name);
            bkstockvalue3 = view.findViewById(R.id.hy_stock_top3_value);
        } else {
            bkname1 = view.findViewById(R.id.gn_top1_name);
            bkvalue1 = view.findViewById(R.id.gn_top1_value);
            bkstockname1 = view.findViewById(R.id.gn_stock_top1_name);
            bkstockvalue1 = view.findViewById(R.id.gn_stock_top1_value);

            bkname2 = view.findViewById(R.id.gn_top2_name);
            bkvalue2 = view.findViewById(R.id.gn_top2_value);
            bkstockname2 = view.findViewById(R.id.gn_stock_top2_name);
            bkstockvalue2 = view.findViewById(R.id.gn_stock_top2_value);

            bkname3 = view.findViewById(R.id.gn_top3_name);
            bkvalue3 = view.findViewById(R.id.gn_top3_value);
            bkstockname3 = view.findViewById(R.id.gn_stock_top3_name);
            bkstockvalue3 = view.findViewById(R.id.gn_stock_top3_value);

        }

        String str_bkname1 = segmentBean.getData().getDiff().getOne().getF14();
        String str_bkname2 = segmentBean.getData().getDiff().getTwo().getF14();
        String str_bkname3 = segmentBean.getData().getDiff().getThree().getF14();   //F14领涨板块名称

        Float bk_value1 = segmentBean.getData().getDiff().getOne().getF3();
        Float bk_value2 = segmentBean.getData().getDiff().getTwo().getF3();
        Float bk_value3 = segmentBean.getData().getDiff().getThree().getF3();    //F3领涨板块涨幅

        String str_stockname1 = segmentBean.getData().getDiff().getOne().getF128();
        String str_stockname2 = segmentBean.getData().getDiff().getTwo().getF128();
        String str_stockname3 = segmentBean.getData().getDiff().getThree().getF128();   //F128领涨股名称

        Float bk_stockvalue1 = segmentBean.getData().getDiff().getOne().getF136();
        Float bk_stockvalue2 = segmentBean.getData().getDiff().getTwo().getF136();
        Float bk_stockvalue3 = segmentBean.getData().getDiff().getThree().getF136();  // //F136领涨板块涨幅

        bkname1.setText(str_bkname1);
        bkname2.setText(str_bkname2);
        bkname3.setText(str_bkname3);

        bkvalue1.setText(String.format("%.2f", bk_value1) + "%");
        setColor(bkvalue1, bk_value1);
        bkvalue2.setText(String.format("%.2f", bk_value2) + "%");
        setColor(bkvalue2, bk_value2);
        bkvalue3.setText(String.format("%.2f", bk_value3) + "%");
        setColor(bkvalue3, bk_value3);

        bkstockname1.setText(str_stockname1);
        bkstockname2.setText(str_stockname2);
        bkstockname3.setText(str_stockname3);

        bkstockvalue1.setText(String.format("%.2f", bk_stockvalue1) + "%");
        setColor(bkvalue1, bk_stockvalue1);
        bkstockvalue2.setText(String.format("%.2f", bk_stockvalue2) + "%");
        setColor(bkvalue2, bk_stockvalue2);
        bkstockvalue3.setText(String.format("%.2f", bk_stockvalue3) + "%");
        setColor(bkvalue3, bk_stockvalue3);


    }

    private void initMystockList() {
        recyview.setLayoutManager(new LinearLayoutManager(getActivity()));
        closeDefaultAnimator(recyview);

        mAdapter = new BaseRecyclerAdapter<MyStockListBean.ResultBean>(list1, R.layout.mystock_item) {
            @Override
            protected void onBindViewHolder(final SmartViewHolder holder, final MyStockListBean.ResultBean model, int position) {
                holder.text(R.id.tv_stock_name, model.getName(), "none", getActivity());
                holder.text(R.id.tv_stock_id, model.getCode());
                if (model.getChangeperc().substring(0, 1).equals("-")) {
                    holder.text(R.id.now_price, model.getTrade(), "green", getActivity());
                    holder.text(R.id.now_percent, model.getChangeperc() + "%", "green", getActivity());
                    holder.text(R.id.now_volum, model.getVolume() + "万手", "green", getActivity());
                } else if (model.getChangeperc().substring(0, 4).equals("0.00")) {
                    holder.text(R.id.now_price, model.getTrade(), "gray", getActivity());
                    holder.text(R.id.now_percent, model.getChangeperc() + "%", "gray", getActivity());
                    holder.text(R.id.now_volum, model.getVolume() + "万手", "gray", getActivity());
                } else {
                    holder.text(R.id.now_price, model.getTrade(), "red", getActivity());
                    holder.text(R.id.now_percent, model.getChangeperc() + "%", "red", getActivity());
                    holder.text(R.id.now_volum, model.getVolume() + "万手", "red", getActivity());
                }


                holder.itemView.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        Bundle b = new Bundle();
                        b.putString("code", model.getCode());
                        b.putString("name", model.getName());
                        b.putString("zixuan", "1");
                        startActivity(StockWeb.class, b);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deleteCodes = model.getCode();
                        showDialog("删除自选股", "确认删除该自选股吗？");
                        return true;
                    }
                });
            }
        };

        mAdapter.setHasStableIds(true);
        recyview.setAdapter(mAdapter);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                queryMyStockList(true);
            }
        });
        refreshLayout.setEnableAutoLoadMore(false);

    }


    public void setColor(TextView textView, Float value) {
        if (value > 0.00) {
            textView.setTextColor(getActivity().getResources().getColor(R.color.stockred));
        } else if (value == 0.00) {
            textView.setTextColor(getActivity().getResources().getColor(R.color.textgray2));
        } else {
            textView.setTextColor(getActivity().getResources().getColor(R.color.stockgreen));
        }
    }

    public void closeDefaultAnimator(RecyclerView mRvCustomer) {
        if (null == mRvCustomer) return;
        mRvCustomer.getItemAnimator().setAddDuration(0);
        mRvCustomer.getItemAnimator().setChangeDuration(0);
        mRvCustomer.getItemAnimator().setMoveDuration(0);
        mRvCustomer.getItemAnimator().setRemoveDuration(0);
        ((SimpleItemAnimator) mRvCustomer.getItemAnimator()).setSupportsChangeAnimations(false);
    }


    public void showDialog(String title, String message) {
        final NoteDialogEx alert = new NoteDialogEx(getActivity());
        alert.setTitle(title);
        alert.setMessageGravity(Gravity.START);
        alert.setMessage(message);
        alert.setCanceledOnTouchOutside(false);  //点击提示框以外的地方不隐藏提示框
        alert.dialog_buttons.setBackgroundResource(R.color.title_blue);
        alert.dialog_buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "";
                Event<String> event = new Event<>(2, s);
                EventBusUtil.sendEvent(event);
                alert.dismiss();
                //showShortToast("点击了确认");
            }
        });
        alert.dialog_buttons1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                //showShortToast("点击了取消");
                deleteCodes = "";
            }
        });
        alert.show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)  //收到标签页传来的数据之后的响应
    public void onMessageEvent(Event load) {
        if (load.getCode() == 2) {  //删除自选
            Log.e("date1", "onMessageEvent: " + load.getData().toString() + "code:" + load.getCode());
            mSVProgressHUD.showWithStatus("删除中");
            deleteMyStock(Constants.userId, deleteCodes);
        }

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}


