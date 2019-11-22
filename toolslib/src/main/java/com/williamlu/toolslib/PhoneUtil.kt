package com.williamlu.toolslib

import android.text.TextUtils

/**
 * @Author: WilliamLu
 * @Date: 2019-05-17
 * @Description:
 */
object PhoneUtil {
    fun phoneSecret(phone: String?): String {
        if (!TextUtils.isEmpty(phone) && phone?.length != 11) {
            return "手机号码错误"
        }
        return phone?.substring(0, 3) + "****" + phone?.substring(phone.length - 4)
    }
}