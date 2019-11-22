package com.williamlu.toolslib

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

/**
 * @Author: WilliamLu
 * @Date: 2018/11/21
 * @Description:检测网络连接工具类
 */
class NetworkChangedReceiver(listener: onChangedListener) : BroadcastReceiver() {
    private var listener: onChangedListener

    init {
        this.listener = listener
    }

    interface onChangedListener {
        fun onNetworkConnected(msg: String)
        fun onNetworkDisconnected(msg: String)
    }

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {

        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            //获取联网状态的NetworkInfo对象
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivityManager.activeNetworkInfo
            if (info != null && info.isConnected) {
                //如果当前的网络连接成功并且网络连接可用
                listener.onNetworkConnected("网络已连接！")
            } else {
                //网络断开连接
                listener.onNetworkDisconnected("网络已断开连接！")
            }
        }
    }
}