package com.williamlu.toolslib

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * @Author: WilliamLu
 * @Data: 2019/4/3
 * @Description:
 */
object IntentSystemUtils {
    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    fun callPhone(context: Context, phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /**
     * url跳转到浏览器
     */
    fun startBrowserByUrl(context: Context, urlStr: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlStr))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}