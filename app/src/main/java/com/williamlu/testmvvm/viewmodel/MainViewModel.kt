package com.williamlu.testmvvm.viewmodel

import android.text.TextUtils
import com.williamlu.testmvvm.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import com.williamlu.datalib.base.ApiException
import com.williamlu.datalib.base.ApiObserver
import com.williamlu.datalib.bean.BaseBean
import com.williamlu.datalib.bean.BaseListBean
import com.williamlu.testmvvm.AppConstant
import com.williamlu.testmvvm.api.service.MainService
import com.williamlu.testmvvm.model.GankBean
import com.williamlu.testmvvm.model.GankDailyData
import com.williamlu.toolslib.ToastUtils


/**
 * @Author: WilliamLu
 * @Date: 2019/11/19
 * @Description:
 */
class MainViewModel : BaseViewModel() {

    fun getGankDaily(): MutableLiveData<GankDailyData> {
        val liveData = MutableLiveData<GankDailyData>()
        MainService.INSTANCE
            .gankDaily()
            .subscribe(object : ApiObserver<BaseBean<GankDailyData>>() {
                override fun onNext(t: BaseBean<GankDailyData>) {
                    liveData.postValue(t.results)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is ApiException) {
                        if (!TextUtils.isEmpty(e.errorMsg)) {
                            ToastUtils.showToast(e.errorMsg)
                        }
                    }
                }
            })
        return liveData
    }

    fun getGankFilter(gankFilterType: String, page: Int): MutableLiveData<List<GankBean>> {
        val liveData = MutableLiveData<List<GankBean>>()
        MainService.INSTANCE
            .gankFilter(gankFilterType, AppConstant.ConfigConstant.SERVICE_PAGE_SIZE.toInt(), page)
            .subscribe(object : ApiObserver<BaseListBean<GankBean>>() {
                override fun onNext(t: BaseListBean<GankBean>) {
                    liveData.postValue(t.results)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is ApiException) {
                        if (!TextUtils.isEmpty(e.errorMsg)) {
                            ToastUtils.showToast(e.errorMsg)
                        }
                    }
                }
            })
        return liveData
    }

}