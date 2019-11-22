package com.williamlu.widgetlib

import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.view.Gravity
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView

/**
 * @Author: WilliamLu
 * @Data: 2019-04-28
 * @Description:
 */
class AppConfigDialog private constructor() {
    private var mDialog: AlertDialog? = null
    private var mContext: Context? = null

    companion object {
        private var INSTANCE: AppConfigDialog? = null

        fun getInstance(): AppConfigDialog {
            synchronized(AppConfigDialog::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = AppConfigDialog()
                }
            }
            return INSTANCE!!
        }
    }

    fun createAlertDialog(context: Context, onDialogClickListener: OnDialogClickListener) {
        createAlertDialog(context, "", "点击输入", onDialogClickListener)
    }

    fun createAlertDialog(context: Context, content: String, onDialogClickListener: OnDialogClickListener) {
        createAlertDialog(context, content, "点击输入", onDialogClickListener)
    }

    fun createAlertDialog(context: Context, content: String, hint: String, onDialogClickListener: OnDialogClickListener) {
        mContext = context
        mDialog = AlertDialog.Builder(mContext!!, R.style.CustomLoadingDialog).create()
        mDialog?.show()
        mDialog?.window?.setContentView(R.layout.dialog_config)
        mDialog?.window?.setGravity(Gravity.CENTER)
        mDialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        mDialog?.setCanceledOnTouchOutside(false)
        mDialog?.setCancelable(false)
        val etContent = mDialog?.findViewById<EditText>(R.id.dialog_config_et_url)
        etContent!!.setText(content)
        etContent.hint = hint
        val btnLeft = mDialog?.findViewById<TextView>(R.id.dialog_config_tv_left)
        val btnRight = mDialog?.findViewById<TextView>(R.id.dialog_config_tv_right)

        btnLeft!!.setOnClickListener {
            mDialog?.dismiss()
        }

        btnRight!!.setOnClickListener {
            val etContent = etContent.text.toString().trim()
            onDialogClickListener.onClickRight(etContent)
            mDialog?.dismiss()
        }

    }

    private var onDialogClickListener: OnDialogClickListener? = null

    fun setOnDialogSelectListener(onDialogClickListener: OnDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener
    }

    interface OnDialogClickListener {
        fun onClickRight(content: String)
    }

}