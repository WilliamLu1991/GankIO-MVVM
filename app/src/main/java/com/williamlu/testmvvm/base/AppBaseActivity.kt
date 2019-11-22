package com.williamlu.testmvvm.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.williamlu.testmvvm.AppConstant
import com.williamlu.testmvvm.R
import com.williamlu.toolslib.SpUtils
import com.williamlu.widgetlib.statusbar.StatusBarCompat

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
abstract class AppBaseActivity<VM : BaseViewModel, VDB : ViewDataBinding> : BaseActivity<VM,VDB>() {

    override fun onInitParams(bundle: Bundle?) {}

    /**
     * 是否需要检查权限 默认不检查
     * 通过 mPermissions 设置要申请的权限
     */
    override fun checkPermission(): Boolean {
        mPermissions = null
        return false
    }

    /**
     * 展示沉浸式白色状态栏和toolbar
     */
    fun showWhiteBar() {
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.color_ffffff))
        StatusBarCompat.changeToLightStatusBar(this)
        mBaseToolBarHelper?.setToolbarBgRes(R.color.color_ffffff)
        mBaseToolBarHelper?.getTitleView()?.setTextColor(resources.getColor(R.color.color_333333))
        mBaseToolBarHelper?.getRightTvView()?.setTextColor(resources.getColor(R.color.color_333333))
    }

    /**
     * 设置透明状态栏
     */
    fun showTranslucentBar() {
        StatusBarCompat.translucentStatusBar(this, true)
        StatusBarCompat.changeToLightStatusBar(this)
    }
}