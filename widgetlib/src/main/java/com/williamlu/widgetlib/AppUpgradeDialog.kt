package com.williamlu.widgetlib

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class AppUpgradeDialog private constructor() {
    private var mDialog: Dialog? = null
    private var mContext: Context? = null

    companion object {
        private var INSTANCE: AppUpgradeDialog? = null

        fun getInstance(): AppUpgradeDialog {
            synchronized(AppUpgradeDialog::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = AppUpgradeDialog()
                }
            }
            return INSTANCE!!
        }
    }

    fun createUpgradeDialog(
        context: Context,
        appVersion: String?,
        content: String?,
        onDialogSelectListener: OnDialogUpgradeListener
    ): Dialog {
        return createUpgradeDialog(context, appVersion, content, true, onDialogSelectListener)
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    fun createUpgradeDialog(
        context: Context,
        appVersion: String?,
        content: String?,
        wouldCancel: Boolean,
        onDialogSelectListener: OnDialogUpgradeListener
    ): Dialog {
        mContext = context
        mDialog = Dialog(mContext!!, R.style.CustomLoadingDialog)
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_app_upgrade, null)
        val tvVersion = view.findViewById<TextView>(R.id.dialog_upgrade_tv_version)
        val tvContent = view.findViewById<TextView>(R.id.dialog_upgrade_tv_content)
        val btnGo = view.findViewById<Button>(R.id.dialog_upgrade_btn_go)
        val ivClose = view.findViewById<ImageView>(R.id.dialog_upgrade_iv_close)

        //tvVersion.text = appVersion
        tvContent.text = content

        mDialog!!.setCanceledOnTouchOutside(wouldCancel)
        mDialog!!.setCancelable(wouldCancel)
        if (wouldCancel) {
            ivClose.visibility = View.VISIBLE
        } else {
            ivClose.visibility = View.GONE
        }

        ivClose.setOnClickListener {
            mDialog!!.dismiss()
        }

        btnGo.setOnClickListener {
            onDialogSelectListener.onUpgradeSelect()
        }

        mDialog!!.setContentView(
            view, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        ) // 设置布局
        mDialog!!.show()
        return mDialog!!
    }

    private var onDialogUpgradeListener: OnDialogUpgradeListener? = null

    fun setOnDialogSelectListener(onDialogUpgradeListener: OnDialogUpgradeListener) {
        this.onDialogUpgradeListener = onDialogUpgradeListener
    }

    interface OnDialogUpgradeListener {
        fun onUpgradeSelect()
    }

}