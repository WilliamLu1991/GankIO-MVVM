package com.williamlu.toolslib

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
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
    fun copy(context: Context, content: String) {
        val cbm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cbm.text = content
        ToastUtils.showToast("复制成功")
    }

    /**
     * 粘贴
     */
    fun Paste(context: Context): String {
        val cbm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return cbm.text.toString().trim()
    }

    /**
     * 获取复制的文本
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
        val cbm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cbm.text = null
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