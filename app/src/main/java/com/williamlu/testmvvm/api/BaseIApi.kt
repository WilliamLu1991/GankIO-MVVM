package com.williamlu.testmvvm.api

import com.williamlu.datalib.bean.BaseBean
import com.williamlu.datalib.bean.BaseListBean
import com.williamlu.testmvvm.model.GankBean
import com.williamlu.testmvvm.model.GankDailyData
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: GankIO http://gank.io
 */
interface BaseIApi {

    @GET("today")
    fun gankDaily(): Observable<BaseBean<GankDailyData>>

    @GET("data/{filterType}/{count}/{page}")
    fun gankFilter(
        @Path("filterType") filterType: String,
        @Path("count") count: Int,
        @Path("page") page: Int
    ): Observable<BaseListBean<GankBean>>

    @GET("search/query/{queryText}/category/all/count/{count}/page/{page}")
    fun search(
        @Path("queryText") queryText: String,
        @Path("count") count: Int,
        @Path("page") page: Int
    ): Observable<BaseListBean<GankBean>>

}