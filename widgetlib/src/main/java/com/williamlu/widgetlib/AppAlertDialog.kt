package com.williamlu.widgetlib

import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class AppAlertDialog private constructor() {
    private var mDialog: AlertDialog? = null
    private var mCanclePingouDialog: AlertDialog? = null
    private var mContext: Context? = null

    companion object {
        private var INSTANCE: AppAlertDialog? = null

        fun getInstance(): AppAlertDialog {
            synchronized(AppAlertDialog::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = AppAlertDialog()
                }
            }
            return INSTANCE!!
        }
    }

    fun createAlertDialog(
        context: Context,
        content: String,
        onDialogSelectListener: OnDialogSelectListener
    ): AlertDialog {
        return createAlertDialog(context, content, "", "取消", "确认", true, onDialogSelectListener)
    }

    fun createAlertDialog(
        context: Context,
        content: String,
        rightStr: String,
        onDialogSelectListener: OnDialogSelectListener
    ): AlertDialog {
        return createAlertDialog(context, content, "", "", rightStr, true, onDialogSelectListener)
    }

    fun createAlertDialog(
        context: Context,
        content: String,
        leftStr: String,
        rightStr: String,
        onDialogSelectListener: OnDialogSelectListener
    ): AlertDialog {
        return createAlertDialog(context, content, "", leftStr, rightStr, true, onDialogSelectListener)
    }

    fun createAlertDialog(
        context: Context,
        content: String,
        rightStr: String,
        wouldCancel: Boolean,
        onDialogSelectListener: OnDialogSelectListener
    ): AlertDialog {
        return createAlertDialog(context, content, "", "", rightStr, wouldCancel, onDialogSelectListener)
    }

    fun createAlertDialog(
        context: Context,
        content: String,
        content2: String,
        leftStr: String,
        rightStr: String,
        onDialogSelectListener: OnDialogSelectListener
    ): AlertDialog {
        return createAlertDialog(context, content, content2, leftStr, rightStr, true, onDialogSelectListener)
    }

    /**
     * 创建取消拼购dialog
     */
    fun createAlertDialogCanclePingou(context: Context):AlertDialog{
        mContext = context
        mCanclePingouDialog = AlertDialog.Builder(mContext!!, R.style.CustomLoadingDialog).create()
        mCanclePingouDialog!!.show()
        mCanclePingouDialog!!.window?.setContentView(R.layout.dialog_app_alert1)
        mCanclePingouDialog!!.window?.setGravity(Gravity.CENTER)
        mCanclePingouDialog!!.window?.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)

        val iknow=mCanclePingouDialog!!.findViewById<TextView>(R.id.iknow)
        iknow!!.setOnClickListener {
            if(null!=mCanclePingouDialog){
                mCanclePingouDialog!!.dismiss()
            }
        }
        return mCanclePingouDialog!!
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
        content: String,
        content2: String,
        leftStr: String,
        rightStr: String,
        wouldCancel: Boolean,
        onDialogSelectListener: OnDialogSelectListener
    ): AlertDialog {
        mContext = context
        mDialog = AlertDialog.Builder(mContext!!, R.style.CustomLoadingDialog).create()
        mDialog!!.show()
        mDialog!!.window?.setContentView(R.layout.dialog_app_alert)
        mDialog!!.window?.setGravity(Gravity.CENTER)
        mDialog!!.window?.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        val tvContent = mDialog!!.findViewById<TextView>(R.id.dialog_app_alert_tv_content)!!
        val flContent2 = mDialog!!.findViewById<FrameLayout>(R.id.dialog_app_alert_fl_content2)!!
        val tvContent2 = mDialog!!.findViewById<TextView>(R.id.dialog_app_alert_tv_content2)!!
        val tvLeft = mDialog!!.findViewById<TextView>(R.id.dialog_app_alert_tv_left)!!
        val tvRight = mDialog!!.findViewById<TextView>(R.id.dialog_app_alert_tv_right)!!
        val vLine = mDialog!!.findViewById<View>(R.id.dialog_app_alert_line)!!

        tvContent.text = content

        if (TextUtils.isEmpty(content2)) {
            flContent2.visibility = View.GONE
        } else {
            flContent2.visibility = View.VISIBLE
            tvContent2.text = content2
        }

        if (TextUtils.isEmpty(leftStr)) {
            tvLeft.visibility = View.GONE
            vLine.visibility = View.GONE
            tvRight.visibility = View.VISIBLE
            tvRight.text = rightStr
        } else {
            tvLeft.visibility = View.VISIBLE
            vLine.visibility = View.VISIBLE
            tvRight.visibility = View.VISIBLE
            tvRight.text = rightStr
        }
        tvLeft.text = leftStr
        tvRight.text = rightStr

        mDialog!!.setCanceledOnTouchOutside(wouldCancel)
        mDialog!!.setCancelable(wouldCancel)


        flContent2.setOnClickListener {
            //onDialogSelectListener.onContent2Select()
            if (wouldCancel) {
                mDialog!!.dismiss()
            }
        }

        tvLeft.setOnClickListener {
            onDialogSelectListener.onLeftSelect()
            mDialog!!.dismiss()

        }
        tvRight.setOnClickListener {
            onDialogSelectListener.onRightSelect()
            mDialog!!.dismiss()
        }

        return mDialog!!
    }

    private var onDialogSelectListener: OnDialogSelectListener? = null

    fun setOnDialogSelectListener(onDialogSelectListener: OnDialogSelectListener) {
        this.onDialogSelectListener = onDialogSelectListener
    }

    interface OnDialogSelectListener {
        //fun onContent2Select()
        fun onLeftSelect()

        fun onRightSelect()
    }

}