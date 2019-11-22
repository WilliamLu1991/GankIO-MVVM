package com.williamlu.testmvvm.base

import com.williamlu.testmvvm.AppConstant
import com.williamlu.testmvvm.R
import com.williamlu.toolslib.SpUtils
import com.williamlu.widgetlib.statusbar.StatusBarCompat

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
abstract class AppBaseFragment : BaseFragment() {

    /**
     * 展示沉浸式白色状态栏和toolbar
     */
    fun showWhiteBar() {
        StatusBarCompat.setStatusBarColor(activity!!, resources.getColor(R.color.color_ffffff))
        StatusBarCompat.changeToLightStatusBar(activity!!)
        mBaseToolBarHelper?.setToolbarBgRes(R.color.color_ffffff)
        mBaseToolBarHelper?.getTitleView()?.setTextColor(resources.getColor(R.color.color_333333))
        mBaseToolBarHelper?.getRightTvView()?.setTextColor(resources.getColor(R.color.color_333333))
    }

    /**
     * 设置透明状态栏
     */
    fun showTranslucentBar() {
        StatusBarCompat.translucentStatusBar(activity!!, true)
        StatusBarCompat.changeToLightStatusBar(activity!!)
    }
}