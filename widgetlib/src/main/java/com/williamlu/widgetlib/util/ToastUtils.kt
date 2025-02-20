package com.williamlu.widgetlib.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.williamlu.widgetlib.R

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
object ToastUtils {
    private var toast: Toast? = null

    fun showToast(context: Context, text: String) {
        dismissToast()
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        val layout = LayoutInflater.from(context).inflate(R.layout.custom_toast, null)
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