package com.example.stockrecommendations.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockrecommendations.R;
import com.example.stockrecommendations.activity.NewsWeb;
import com.example.stockrecommendations.adapter.BaseRecyclerAdapter;
import com.example.stockrecommendations.adapter.SmartViewHolder;
import com.example.stockrecommendations.api.RetrofitApi;
import com.example.stockrecommendations.base.BaseFragment;
import com.example.stockrecommendations.entity.NewsBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends BaseFragment {

    private View view;
    private BaseRecyclerAdapter<NewsBean.Result.nList> mAdapter;
    private List<NewsBean.Result.nList> list1;
    private RefreshLayout refreshLayout;
    private View recyview;
    private int firstIn = 1;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        initData();
        return view;
    }


    private void initView() {
        setTop(view);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableFooterFollowWhenNoMoreData(true);
        recyview = view.findViewById(R.id.recyclerView);
        queryNews(0);
    }

    private void initData() {

    }


    private void queryNews(final int isRefresh) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RetrofitApi service = retrofit.create(RetrofitApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("key", "dbfd39d056b76d2a61570e0d7d92e2ae");
        params.put("type", "caijing");

        Call<NewsBean> call = service.getNews(params);
        call.enqueue(new Callback<NewsBean>() {
            @Override
            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
                Log.e("数据添加", "成功?????" + response.body().getReason());
                if (response.body() != null) {
                    if (response.body().getError_code() == 0) {
                        if (firstIn == 1) {
                            list1 = response.body().getResult().getData();
                            initList();
                            firstIn = 0;
                        } else {
                            if (isRefresh == 1) {
                                refreshLayout.getLayout().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshLayout.finishRefresh();
                                        refreshLayout.resetNoMoreData();//setNoMoreData(false);//恢复上拉状态
                                    }
                                }, 1000);
                                mAdapter.refresh(response.body().getResult().getData());
                            } else {
                                mAdapter.loadMore(response.body().getResult().getData());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsBean> call, Throwable t) {
                Log.e("数据添加失败", "" + t);
                showShortToast("加载数据失败");
            }
        });
    }


    private void initList() {
        if (recyview instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) recyview;
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter = new BaseRecyclerAdapter<NewsBean.Result.nList>(list1, R.layout.news_item) {

                @Override
                protected void onBindViewHolder(SmartViewHolder holder, final NewsBean.Result.nList model, int position) {
                    holder.text(R.id.tx1, model.getTitle());
                    holder.text(R.id.tx2, model.getAuthor_name());
                    holder.setimage(R.id.img, model.getThumbnail_pic_s(), getActivity());
                    holder.text(R.id.tx3, model.getDate());

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle b = new Bundle();
                            b.putString("url", model.getUrl());
                            startActivity(NewsWeb.class, b);
                        }
                    });
                }


            });


            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    queryNews(1);
                }
            });
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshlayout) {
                    // queryNews(0);
                    refreshLayout.getLayout().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.finishLoadMore();//传入false表示加载失败
                            showShortToast("没有更多数据了");
                        }
                    }, 1000);

                }
            });
        }
    }

}
