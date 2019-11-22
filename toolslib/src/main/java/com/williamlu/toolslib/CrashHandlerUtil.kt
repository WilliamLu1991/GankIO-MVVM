package com.williamlu.toolslib

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * @Author: WilliamLu
 * @Date: 2019-04-29
 * @Description:
 */
class CrashHandlerUtil private constructor() : Thread.UncaughtExceptionHandler {

    private var mDefaultCrashHandler: Thread.UncaughtExceptionHandler? = null
    private var mContext: Context? = null
    // 保存手机信息和异常信息
    private var mMessage = HashMap<String, String>()

    companion object {

        private var INSTANCE: CrashHandlerUtil? = null

        fun getInstance(): CrashHandlerUtil {
            synchronized(CrashHandlerUtil::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = CrashHandlerUtil()
                }
            }
            return INSTANCE!!
        }
    }

    fun init(context: Context) {
        mContext = context
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        if (!handleException(e)) {
            // 未经过人为处理,则调用系统默认处理异常,弹出系统强制关闭的对话框
            if (mDefaultCrashHandler != null) {
                mDefaultCrashHandler?.uncaughtException(t, e)
            }
        } else {
            // 已经人为处理,系统自己退出
            try {
                Thread.sleep(1000)
            } catch (e1: InterruptedException) {
                e1.printStackTrace()
            }

            System.exit(1)
        }
    }

    /**
     * 是否人为捕获异常
     *
     * @param e Throwable
     * @return true:已处理 false:未处理
     */
    private fun handleException(e: Throwable?): Boolean {
        if (e == null) {// 异常是否为空
            return false
        }
        object : Thread() {
            // 在主线程中弹出提示
            override fun run() {
                Looper.prepare()
                Toast.makeText(mContext, "捕获到异常", Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
        }.start()
        collectErrorMessages()
        saveErrorMessages(e)
        return false
    }

    /**
     * 1.收集错误信息
     */
    private fun collectErrorMessages() {
        val pm = mContext!!.getPackageManager()
        try {
            val pi = pm.getPackageInfo(mContext!!.getPackageName(), PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (TextUtils.isEmpty(pi.versionName)) "null" else pi.versionName
                val versionCode = "" + pi.versionCode
                mMessage.put("versionName", versionName)
                mMessage.put("versionCode", versionCode)
            }
            // 通过反射拿到错误信息
            val fields = Build::class.java.fields
            if (fields != null && fields.size > 0) {
                for (field in fields) {
                    field.isAccessible = true
                    try {
                        mMessage.put(field.name, field.get(null).toString())
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }

                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }

    /**
     * 2.保存错误信息
     *
     * @param e Throwable
     */
    private fun saveErrorMessages(e: Throwable) {
        val sb = StringBuilder()
        for (entry in mMessage.entries) {
            val key = entry.key
            val value = entry.value
            sb.append(key).append("=").append(value).append("\n")
        }
        val writer = StringWriter()
        val pw = PrintWriter(writer)
        e.printStackTrace(pw)
        pw.close()
        val result = writer.toString()
        sb.append(result)
        //val fileName = "crash-" + System.currentTimeMillis() + "-" + ".log"
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(Date())
        val fileName = "crash-" + time + "-" + ".log"
        // 有无SD卡
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val path = Environment.getExternalStorageDirectory().path + "/crash/"
            val dir = File(path)
            if (!dir.exists()) dir.mkdirs()
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(path + fileName)
                fos.write(sb.toString().toByteArray())
            } catch (e1: Exception) {
                e1.printStackTrace()
            } finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e1: IOException) {
                        e1.printStackTrace()
                    }

                }
            }
        }
    }

}