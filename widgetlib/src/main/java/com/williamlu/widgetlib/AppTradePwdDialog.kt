package com.williamlu.widgetlib

import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.williamlu.widgetlib.util.ToastUtils

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class AppTradePwdDialog private constructor() {
    private var mDialog: AlertDialog? = null
    private var mContext: Context? = null

    companion object {
        private var INSTANCE: AppTradePwdDialog? = null

        fun getInstance(): AppTradePwdDialog {
            synchronized(AppTradePwdDialog::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = AppTradePwdDialog()
                }
            }
            return INSTANCE!!
        }
    }

    fun createAlertDialog(
        context: Context,
        payMoney: String,
        onDialogConfirmListener: OnDialogConfirmListener
    ): AlertDialog {
        return createAlertDialog(context, payMoney, false, onDialogConfirmListener)
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    fun createAlertDialog(
        context: Context,
        payMoney: String,
        wouldCancel: Boolean,
        onDialogSelectListener: OnDialogConfirmListener
    ): AlertDialog {
        mContext = context
        mDialog = AlertDialog.Builder(mContext!!, R.style.CustomLoadingDialog).create()
        mDialog?.show()
        mDialog?.window?.setContentView(R.layout.dialog_app_trade_pwd)
        mDialog?.window?.setGravity(Gravity.CENTER)
        mDialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        val tvPayMoney = mDialog?.findViewById<TextView>(R.id.tradepwd_tv_pay_money)
        val tvForgetPwd = mDialog?.findViewById<TextView>(R.id.tradepwd_tv_forget_pwd)
        val btnConfirm = mDialog?.findViewById<Button>(R.id.tradepwd_btn_confirm)
        val ivColse = mDialog?.findViewById<ImageView>(R.id.tradepwd_iv_close)
        val etWritePwd = mDialog?.findViewById<EditText>(R.id.tradepwd_et_write_pwd)
        val tvWritePwd1 = mDialog?.findViewById<TextView>(R.id.tradepwd_tv_write_pwd1)
        val tvWritePwd2 = mDialog?.findViewById<TextView>(R.id.tradepwd_tv_write_pwd2)
        val tvWritePwd3 = mDialog?.findViewById<TextView>(R.id.tradepwd_tv_write_pwd3)
        val tvWritePwd4 = mDialog?.findViewById<TextView>(R.id.tradepwd_tv_write_pwd4)
        val tvWritePwd5 = mDialog?.findViewById<TextView>(R.id.tradepwd_tv_write_pwd5)
        val tvWritePwd6 = mDialog?.findViewById<TextView>(R.id.tradepwd_tv_write_pwd6)

        var tvList: MutableList<TextView> = ArrayList()
        tvList.add(tvWritePwd1!!)
        tvList.add(tvWritePwd2!!)
        tvList.add(tvWritePwd3!!)
        tvList.add(tvWritePwd4!!)
        tvList.add(tvWritePwd5!!)
        tvList.add(tvWritePwd6!!)

        tvPayMoney?.text = "¥$payMoney"

        mDialog?.setCanceledOnTouchOutside(wouldCancel)
        mDialog?.setCancelable(wouldCancel)

        ivColse?.setOnClickListener {
            onDialogSelectListener.onCloseClick()
        }

        etWritePwd?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString().trim()
                val chars = text.toCharArray()
                val length = chars.size
                val tvLength = tvList.size
                for (i in 0 until tvLength) {
                    if (i < length) {
                        tvList[i].setText("" + chars[i])
                    } else {
                        tvList[i].setText("")
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        btnConfirm?.setOnClickListener {
            val tradePwd = etWritePwd?.text.toString().trim()
            if (TextUtils.isEmpty(tradePwd)) {
                ToastUtils.showToast(context, "您还未填写验证码哦～")
                return@setOnClickListener
            }
            if (tradePwd.length != 6) {
                ToastUtils.showToast(context, "请输入6位交易密码")
                return@setOnClickListener
            }
            onDialogSelectListener.onBtnClick(tradePwd)
        }

        tvForgetPwd?.setOnClickListener {
            onDialogSelectListener.onForgetPwdClick()
        }

        return mDialog!!
    }

    private var onDialogSelectListener: OnDialogConfirmListener? = null

    fun setOnDialogSelectListener(onDialogSelectListener: OnDialogConfirmListener) {
        this.onDialogSelectListener = onDialogSelectListener
    }

    interface OnDialogConfirmListener {
        fun onBtnClick(tradePwd: String)
        fun onForgetPwdClick()
        fun onCloseClick()
    }

}