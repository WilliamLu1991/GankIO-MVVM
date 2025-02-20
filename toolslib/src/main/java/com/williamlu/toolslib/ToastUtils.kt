package com.williamlu.toolslib

import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
object ToastUtils {
    private var toast: Toast? = null

    fun showToast(text: String) {
        dismissToast()

        toast = Toast.makeText(GlobalCache.getContext(), text, Toast.LENGTH_SHORT)
        val layout = LayoutInflater.from(GlobalCache.getContext()).inflate(R.layout.custom_toast, null)
        val tv = layout.findViewById(R.id.toast_context) as TextView
        tv.text = text
        toast!!.setGravity(Gravity.CENTER, 0, 0)
        toast!!.view = layout

        toast!!.show()
    }

    fun dismissToast() {
        if (toast != null) {
            toast!!.cancel()
        }
        toast = null
    }
}