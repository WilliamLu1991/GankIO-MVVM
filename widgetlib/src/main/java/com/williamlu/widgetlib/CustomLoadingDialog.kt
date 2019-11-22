package com.williamlu.widgetlib

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class CustomLoadingDialog private constructor() {
    private var mDialog: Dialog? = null

    companion object {
        private var INSTANCE: CustomLoadingDialog? = null

        fun getInstance(): CustomLoadingDialog {
            synchronized(CustomLoadingDialog::class.java) {
                if (INSTANCE == null) {
                    INSTANCE =
                            CustomLoadingDialog()
                }
            }
            return INSTANCE!!
        }
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    fun createLoadingDialog(context: Context, msg: String): Dialog {
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_load, null) // 得到加载view
        val layout = v.findViewById<LinearLayout>(R.id.dialog_load_ll) // 加载布局
        // main.xml中的ImageView
        val pbLoading = v.findViewById<ProgressBar>(R.id.dialog_load_pb)
        val ivLoading = v.findViewById<ImageView>(R.id.dialog_load_iv)
        val tipTextView = v.findViewById<TextView>(R.id.dialog_load_tv) // 提示文字
        //Glide.with(context).load(R.drawable.gif_loading).into(ivLoading)
        if (TextUtils.isEmpty(msg)) {
            tipTextView.text = "加载中..."
        } else {
            tipTextView.text = msg // 设置加载信息
        }
        mDialog = Dialog(context, R.style.CustomLoadingDialog) // 创建自定义样式dialog

        mDialog!!.setCancelable(false) // 不可以用“返回键”取消
        mDialog!!.setCanceledOnTouchOutside(false)
        mDialog!!.setContentView(layout, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)) // 设置布局
        return mDialog!!

    }

}