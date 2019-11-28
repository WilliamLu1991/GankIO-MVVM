package com.williamlu.testmvvm.base

import androidx.lifecycle.ViewModel

/**
 * @Author: WilliamLu
 * @Date: 2019/11/19
 * @Description:
 */
abstract class BaseViewModel : ViewModel() {
    /*fun  <T> Single<T>.bindLifeCycle(owner: LifecycleOwner): SingleSubscribeProxy<T> {
        return this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))
    }*/
}