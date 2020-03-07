package com.example.stockrecommendations.api;

import com.example.stockrecommendations.entity.AddMyStockBean;
import com.example.stockrecommendations.entity.DeleteStockBean;
import com.example.stockrecommendations.entity.LoginBean;
import com.example.stockrecommendations.entity.MyStockListBean;
import com.example.stockrecommendations.entity.PostLableBean;
import com.example.stockrecommendations.entity.SegmentBean;
import com.example.stockrecommendations.entity.NewsBean;
import com.example.stockrecommendations.entity.StockListBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RetrofitApi {
    @FormUrlEncoded
    @POST("toutiao/index")
    Call<NewsBean> getNews(@FieldMap Map<String, String> map);

    @GET("list={stock}")
//新浪刷新三大股指
    Call<String> getStock(@Path("stock") String stocklist);
    //http://45.push2.eastmoney.com/api/qt/clist/get?pn=1&pz=3&po=1&fltt=2&fid=f3&fs=m:90+t:2&fields=f3,f14,f128,f136,f140

    @GET("api/qt/clist/get")
//东方财富刷新行业板块
    Call<SegmentBean> getIndusty(@Query("pn") String page, @Query("pz") String num, @Query("po") String sum, @Query("fltt") String fltt,
                                 @Query("fid") String fid, @Query("fs") String fs, @Query("fields") String fields);

    //http://12.push2.eastmoney.com/api/qt/clist/get?pn=1&pz=3&po=1&fltt=2&fid=f3&fs=m:90+t:3&fields=f3,f14,f128,f136,f140
    @GET("api/qt/clist/get")
//东方财富刷新概念板块
    Call<SegmentBean> getConcept(@Query("pn") String page, @Query("pz") String num, @Query("po") String sum, @Query("fltt") String fltt,
                                 @Query("fid") String fid, @Query("fs") String fs, @Query("fields") String fields);

    //http://47.102.133.119:8080/stock/app/getalltablelistwithpage
    @FormUrlEncoded
    @POST("getalltablelistwithpage")
    Call<StockListBean> getStocklist(@FieldMap Map<String, String> map);

    @POST("gettablelistwithpage")
    Call<StockListBean> getLableStock(@Body PostLableBean postLableBean);//@body即非表单请求体，被@Body注解的ask将会被Gson转换成json

    @FormUrlEncoded
    @POST("getcolstock")
    Call<MyStockListBean> getMysStockList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("addcollectstock")
    Call<AddMyStockBean> addMyStock(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("applogin")
    Call<LoginBean> toLogin(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("delcolstock")
    Call<DeleteStockBean> toDelete(@FieldMap Map<String, String> map);


}
