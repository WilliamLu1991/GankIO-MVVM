package com.williamlu.datalib.base

import com.williamlu.datalib.bean.BaseListBean
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class DefaultListTransformer<T> : ObservableTransformer<T, T> {

    override fun apply(tObservable: Observable<T>): Observable<T> {
        return tObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { t ->
                    if ((t as BaseListBean<*>).error) {
                        throw ApiException((t as BaseListBean<*>).code, (t as BaseListBean<*>).msg)
                    }
                    t
                }
    }
}