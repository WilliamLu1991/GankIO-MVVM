package com.williamlu.testmvvm.api.service

import com.williamlu.datalib.base.DefaultListTransformer
import com.williamlu.datalib.base.DefaultTransformer
import com.williamlu.datalib.bean.BaseBean
import com.williamlu.datalib.bean.BaseListBean
import com.williamlu.testmvvm.AppConstant
import com.williamlu.testmvvm.api.BaseApiService
import com.williamlu.testmvvm.api.apiinterface.AppIMain
import com.williamlu.testmvvm.model.GankBean
import com.williamlu.testmvvm.model.GankDailyData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class MainService private constructor() : BaseApiService(), AppIMain {

    companion object {
        val INSTANCE: MainService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MainService()
        }
    }

    override fun gankDaily(): Observable<BaseBean<GankDailyData>> {
        return AppConstant.getBaseApiService()
            .gankDaily()
            .compose(DefaultTransformer<BaseBean<GankDailyData>>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun gankFilter(filterType: String, count: Int, page: Int): Observable<BaseListBean<GankBean>> {
        return AppConstant.getBaseApiService()
            .gankFilter(filterType, count, page)
            .compose(DefaultListTransformer<BaseListBean<GankBean>>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun search(queryText: String, count: Int, page: Int): Observable<BaseListBean<GankBean>> {
        return AppConstant.getBaseApiService()
            .search(queryText, count, page)
            .compose(DefaultListTransformer<BaseListBean<GankBean>>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}