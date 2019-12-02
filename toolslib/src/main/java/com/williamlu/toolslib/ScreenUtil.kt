package com.williamlu.toolslib

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager


/**
 * @Author: WilliamLu
 * @Date: 2019/12/2
 * @Description:
 */
object ScreenUtil {
    fun getAndroidScreenProperty(context: Context) {
        val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val width: Int = dm.widthPixels // 屏幕宽度（像素）
        val height: Int = dm.heightPixels // 屏幕高度（像素）
        val density: Float = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
        val densityDpi: Int = dm.densityDpi // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        val screenWidth = (width / density).toInt() // 屏幕宽度(dp)
        val screenHeight = (height / density).toInt() // 屏幕高度(dp)

        Log.d("ScreenUtil", "屏幕宽度（像素）：$width")
        Log.d("ScreenUtil", "屏幕高度（像素）：$height")
        Log.d("ScreenUtil", "屏幕密度（0.75 / 1.0 / 1.5）：$density")
        Log.d("ScreenUtil", "屏幕密度dpi（120 / 160 / 240）：$densityDpi")
        Log.d("ScreenUtil", "屏幕宽度（dp）：$screenWidth")
        Log.d("ScreenUtil", "屏幕高度（dp）：$screenHeight")
    }

    fun getScreenWidthPx(context: Context): Int {
        val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    fun getScreenHeightPx(context: Context): Int {
        val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }

    fun getScreenWidthDp(context: Context): Int {
        val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val width: Int = dm.widthPixels // 屏幕宽度（像素）
        val density: Float = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        return (width / density).toInt()
    }

    fun getScreenHeightDp(context: Context): Int {
        val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val height: Int = dm.heightPixels // 屏幕高度（像素）
        val density: Float = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        return (height / density).toInt()
    }
}