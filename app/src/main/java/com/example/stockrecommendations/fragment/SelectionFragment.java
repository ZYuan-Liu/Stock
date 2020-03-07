package com.example.stockrecommendations.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.stockrecommendations.R;
import com.example.stockrecommendations.activity.LableActivity;
import com.example.stockrecommendations.adapter.ContentAdapter;
import com.example.stockrecommendations.adapter.ContentAdapter.OnContentScrollListener;
import com.example.stockrecommendations.adapter.TopTabAdpater;
import com.example.stockrecommendations.api.RetrofitApi;
import com.example.stockrecommendations.base.BaseFragment;
import com.example.stockrecommendations.entity.Event;
import com.example.stockrecommendations.entity.PostLableBean;
import com.example.stockrecommendations.entity.SelectionBean;
import com.example.stockrecommendations.entity.StockListBean;
import com.example.stockrecommendations.util.Constants;
import com.example.stockrecommendations.util.CustomHorizontalScrollView;
import com.example.stockrecommendations.util.NoDoubleClickListener;
import com.example.stockrecommendations.util.onLoadMoreListener;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SelectionFragment extends BaseFragment implements OnContentScrollListener {
    private View view;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rvTabRight;
    private CustomHorizontalScrollView horScrollview;
    private RecyclerView recyclerContent;
    private TextView searchText;
    private Boolean isRefresh = false;
    private int pagenum = 1;
    private SVProgressHUD mSVProgressHUD;
    private Boolean isLable = false;
    private List<SelectionBean> mEntities = new ArrayList<>();
    private List<String> topTabs = new ArrayList<String>() {{   //标题栏
        add("行业板块");
        add("公司规模");
        add("地区板块");
        add("财务指标分数1");
        add("财务指标分数2");
        add("非财指标分数1");
        add("非财指标分数2");
    }};
    private ContentAdapter contentAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_selection, container, false);
        contentAdapter = new ContentAdapter(getActivity());
        EventBus.getDefault().register(this);
        initView();
        initData();
        initClick();
        mSVProgressHUD.showWithStatus("查询中");
        requstAllstock(pagenum, false, false, new PostLableBean());
        return view;
    }


    private void initView() {
        setTop(view);
        mSVProgressHUD = new SVProgressHUD(getActivity());
        searchText = view.findViewById(R.id.ed_text);
        rvTabRight = view.findViewById(R.id.rv_tab_right);
        horScrollview = view.findViewById(R.id.hor_scrollview);
        recyclerContent = view.findViewById(R.id.recycler_content);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setEnabled(false);

    }

    private void initClick() {

        final ContentAdapter contentAdapter = new ContentAdapter(getActivity());
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requstAllstock(1, true, isLable, new PostLableBean());
            }
        });

        recyclerContent.addOnScrollListener(new onLoadMoreListener() {
            @Override
            protected void onLoading(int countItem, int lastItem) {
                requstAllstock(pagenum, false, isLable, new PostLableBean());
            }
        });


        //滚动RV时,同步所有横向位移的item
        recyclerContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                List<ContentAdapter.ItemViewHolder> viewHolderCacheList = contentAdapter.getViewHolderCacheList();
                if (null != viewHolderCacheList) {
                    int size = viewHolderCacheList.size();
                    for (int i = 0; i < size; i++) {
                        viewHolderCacheList.get(i).horItemScrollview.scrollTo(contentAdapter.getOffestX(), 0);
                    }
                }

            }
        });

        //同步顶部tab的横向scroll和内容页面的横向滚动
        //同步滚动顶部tab和内容
        horScrollview.setOnCustomScrollChangeListener(new CustomHorizontalScrollView.OnCustomScrollChangeListener() {
            @Override
            public void onCustomScrollChange(CustomHorizontalScrollView listener, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //代码重复,可以抽取/////
                List<ContentAdapter.ItemViewHolder> viewHolderCacheList = contentAdapter.getViewHolderCacheList();
                if (null != viewHolderCacheList) {
                    int size = viewHolderCacheList.size();
                    for (int i = 0; i < size; i++) {
                        viewHolderCacheList.get(i).horItemScrollview.scrollTo(scrollX, 0);
                    }
                }
            }

        });


        searchText.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                startActivity(LableActivity.class);
            }
        });


    }

    private void initData() {
        //处理顶部标题部分
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTabRight.setLayoutManager(linearLayoutManager);
        TopTabAdpater topTabAdpater = new TopTabAdpater(getActivity());
        rvTabRight.setAdapter(topTabAdpater);

        topTabAdpater.setDatas(topTabs);
        //处理内容部分
        recyclerContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerContent.setHasFixedSize(true);

        recyclerContent.setAdapter(contentAdapter);
        contentAdapter.setOnContentScrollListener(this);


    }


    @Override
    public void onScroll(int offestX) {
        //处理单个item滚动时,顶部tab需要联动
        if (null != horScrollview) horScrollview.scrollTo(offestX, 0);
    }

    public void requstAllstock(final int pagenum, final Boolean isRefresh, final Boolean isLable, final PostLableBean lableJson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi service = retrofit.create(RetrofitApi.class);


        if (isLable) {
            Call<StockListBean> call = service.getLableStock(lableJson);
            call.enqueue(new Callback<StockListBean>() {
                @Override
                public void onResponse(Call<StockListBean> call, Response<StockListBean> response) {
                    if (response.body().getStateCode().equals("10001") || response.body().getResult() == null) {
                        showShortToast("未查询到相关数据");
                    } else {
                        if (isRefresh) {
                            swipeRefresh.setRefreshing(false);
                            addData(response.body().getResult(), isRefresh);
                            showShortToast("刷新完毕");   //刷新功能没用，暂时屏蔽了，代码留着
                        } else {
                            addData(response.body().getResult(), false);
                        }
                    }
                    mSVProgressHUD.dismiss();
                }

                @Override
                public void onFailure(Call<StockListBean> call, Throwable t) {
                    Log.e("标签股票列表接口查询失败", t.toString());
                    showShortToast("网络连接失败");
                    mSVProgressHUD.dismiss();
                }
            });
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("currPage", String.valueOf(pagenum));
            Call<StockListBean> call = service.getStocklist(params);
            call.enqueue(new Callback<StockListBean>() {
                @Override
                public void onResponse(Call<StockListBean> call, Response<StockListBean> response) {
                    if (response.body().getStateCode().equals("10001") || response.body().getResult() == null) {
                        showShortToast("未查询到更多数据");
                    } else {
                        if (isRefresh) {
                            swipeRefresh.setRefreshing(false);
                            addData(response.body().getResult(), isRefresh);
                            showShortToast("刷新完毕");   //刷新功能没用，暂时屏蔽了，代码留着
                        } else {
                            addData(response.body().getResult(), false);
                        }
                    }

                    mSVProgressHUD.dismiss();
                }

                @Override
                public void onFailure(Call<StockListBean> call, Throwable t) {
                    Log.e("股票列表接口查询失败", t.toString());
                    showShortToast("网络连接失败");
                    mSVProgressHUD.dismiss();
                }
            });
        }


    }

    public void addData(List<StockListBean.Result> result, Boolean isRefresh) {
        if (isRefresh) {
            pagenum = 1;
            mEntities.clear();
        }
        if (isLable && pagenum == 1) {
            mEntities.clear();
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        for (int j = 0; j < result.size(); j++) {
            SelectionBean entity = new SelectionBean();
            entity.setLeftTitle(result.get(j).getJc());
            entity.setLeftCode(result.get(j).getCode());
            List<String> rightMoveDatas = new ArrayList<>();
            rightMoveDatas.add(result.get(j).getHybk());
            rightMoveDatas.add(result.get(j).getGsgm());
            rightMoveDatas.add(result.get(j).getDqbk());
            rightMoveDatas.add(df.format(result.get(j).getScore_1()));
            rightMoveDatas.add(df.format(result.get(j).getScore_2()));
            rightMoveDatas.add(df.format(result.get(j).getScore_3()));
            rightMoveDatas.add(df.format(result.get(j).getScore_4()));   //去对接口文档吧

            entity.setRightDatas(rightMoveDatas);
            mEntities.add(entity);

        }
        pagenum++;
        contentAdapter.setDatas(mEntities);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)  //收到标签页传来的数据之后的响应
    public void onMessageEvent(Event load) {

        if (load.getCode() == 1) {
            Log.e("date1", "onMessageEvent: " + load.getData().toString() + "code:" + load.getCode());
            mSVProgressHUD.showWithStatus("刷新中");
            pagenum = 1;
            isLable = true;
            Gson gson = new Gson();
            PostLableBean postLableBean = gson.fromJson(load.getData().toString(), PostLableBean.class);  //不要问我为啥转成json字符串又转回实体..因为retrofit那边不好处理
            requstAllstock(pagenum, false, true, postLableBean);
            mSVProgressHUD.showWithStatus("查询中");
        }

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
