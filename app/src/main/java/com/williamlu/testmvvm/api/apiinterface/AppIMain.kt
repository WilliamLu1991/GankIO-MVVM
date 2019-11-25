package com.williamlu.testmvvm.api.apiinterface

import com.williamlu.datalib.bean.BaseBean
import com.williamlu.datalib.bean.BaseListBean
import com.williamlu.testmvvm.model.GankBean
import com.williamlu.testmvvm.model.GankDailyData
import io.reactivex.Observable

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
interface AppIMain {

    fun gankDaily(): Observable<BaseBean<GankDailyData>>

    fun gankFilter(filterType: String, count: Int, page: Int): Observable<BaseListBean<GankBean>>

    fun search(queryText: String, count: Int, page: Int): Observable<BaseListBean <GankBean>>

}