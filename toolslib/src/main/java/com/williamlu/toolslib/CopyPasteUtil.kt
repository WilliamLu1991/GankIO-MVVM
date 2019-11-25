package com.williamlu.toolslib

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.text.TextUtils
import androidx.annotation.NonNull
import androidx.annotation.Nullable

/**
 * @Author: WilliamLu
 * @Date: 2019-05-17
 * @Description:
 */
object CopyPasteUtil {
    /**
     * 复制
     */
    fun copy(context: Context, content: String, isShowToast: Boolean = true) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val mClipData: ClipData = ClipData.newPlainText("Label", content)
        cm?.primaryClip = mClipData
        if (isShowToast && cm != null) {
            ToastUtils.showToast("复制成功")
        }
    }

    /**
     * 获取复制的文本（粘贴） 支持 android O
     * 使用：CopyPasteUtil.getClipboardText(this, object : (String) -> Unit {override fun invoke(copyText: String) { }}
     */
    fun getClipboardText(@Nullable activity: Activity, f: (String) -> Unit) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            getTextFromClipFromAndroidQ(activity, f)
        } else {
            f.invoke(getTextFromClip(activity))
        }
    }

    /**
     * 清空剪切板内容
     */
    fun clearClipboard(context: Context) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val mClipData: ClipData = ClipData.newPlainText("Label", null)
        cm?.primaryClip = mClipData
    }

    /**
     * AndroidQ 获取剪贴板的内容
     */
    private fun getTextFromClipFromAndroidQ(@NonNull activity: Activity, f: (String) -> Unit) {
        activity.window?.decorView?.post {
            try {
                val clipboardManager =
                    activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                if (null == clipboardManager || !clipboardManager.hasPrimaryClip()) {
                    f.invoke("")
                    return@post
                }
                val clipData = clipboardManager.primaryClip
                if (null == clipData || clipData.itemCount < 1) {
                    f.invoke("")
                    return@post
                }
                val clipText = clipData.getItemAt(0)?.text ?: ""
                f.invoke(clipText.toString())
                return@post
            } catch (e: Exception) {
                f.invoke("")
                return@post
            }
        } ?: f.invoke("")
    }

    private fun getTextFromClip(context: Context): String {
        try {
            //可以使用Application的Context
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            if (null == clipboardManager || !clipboardManager.hasPrimaryClip()) {
                return ""
            }
            val clipData = clipboardManager.primaryClip
            if (null == clipData || clipData.itemCount < 1) {
                return ""
            }
            val item = clipData.getItemAt(0) ?: return ""
            val clipText = item.text ?: ""
            return if (TextUtils.isEmpty(clipText)) "" else clipText.toString()
        } catch (e: Exception) {
            return ""
        }
    }
}