package com.williamlu.testmvvm.base

/**
 * @Author: WilliamLu
 * @Date: 2018/11/23
 * @Description:
 */
interface BaseLoadView {
    /**
     * 显示加载中动画
     */
    fun showLoadingDialog()

    /**
     * 隐藏加载中动画
     */
    fun dismissLoadingDialog()

    /**
     * 显示加载中View
     */
    fun showLoadingView()

    /**
     * 显示无数据带描述View
     */
    fun showEmptyDataDesView(des: String)

    fun showEmptyDataDesView(des: String, resPic: Int)

    /**
     * 隐藏所有空View
     */
    fun dismissAllView()

    /**
     * 显示无数据View
     */
    fun showEmptyDataView()

    /**
     * 显示错误View
     */
    fun showErrorView()

    /**
     * 显示Swipe
     */
    fun showSwipeRl()

    /**
     * 隐藏Swipe
     */
    fun dismissSwipeRl()

    /**
     * 隐藏Swipe和LoadingDialog
     */
    fun dismissSwipeRlAndLoadingDialog()
}