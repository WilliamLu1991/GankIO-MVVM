package com.williamlu.testmvvm

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.multidex.MultiDex
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.williamlu.toolslib.GlobalCache
import com.williamlu.toolslib.NetworkChangedReceiver
import com.williamlu.toolslib.SpUtils
import com.williamlu.toolslib.ToastUtils


/**
 * @Author: WilliamLu
 * @Date: 2018/11/21
 * @Description:
 */
class AppApplation : Application() {
    companion object {
        private var mReceiver: NetworkChangedReceiver? = null
        fun offNetworkReceiver() {
            GlobalCache.getContext().unregisterReceiver(mReceiver)
        }
    }

    override fun onCreate() {
        super.onCreate()
        initGlobalCache()
        //初始化URL
        initUrl()
        //初始化日志
        initLogger()
        //初始化网络监听
        initNetwork()
        initMultiDex()
        initLeakCanary()
    }

    private fun initUrl() {
        val mSpUtils = SpUtils.getInstance(AppConstant.SpConstant.CONFIG_INFO)
        if (BuildConfig.DEBUG) {
            mSpUtils.put(AppConstant.SpConstant.CONFIG_BASEURL, AppConstant.UrlConstant.URL_BASE_TEST)
        } else {
            mSpUtils.put(AppConstant.SpConstant.CONFIG_BASEURL, AppConstant.UrlConstant.URL_BASE_PRODUCT)
        }
    }

    private fun initLeakCanary() {
        /*if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return
            }
            LeakCanary.install(this)
        }*/
    }

    private fun initLogger() {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private fun initGlobalCache() {
        GlobalCache.getInstance().registerContext(this)
    }

    private fun initMultiDex() {
        MultiDex.install(this)
    }

    private fun initNetwork() {
        mReceiver = NetworkChangedReceiver(object : NetworkChangedReceiver.onChangedListener {
            override fun onNetworkConnected(msg: String) {
                //ToastUtils.showToast(msg)
            }

            override fun onNetworkDisconnected(msg: String) {
                ToastUtils.showToast(msg)
            }
        })
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(mReceiver, filter)
    }
}