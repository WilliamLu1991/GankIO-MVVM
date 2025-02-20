package com.williamlu.testmvvm.base

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class ActivityCacheManager private constructor() {
    companion object {
        private var INSTANCE: ActivityCacheManager? = null

        fun getInstance(): ActivityCacheManager {
            synchronized(ActivityCacheManager::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = ActivityCacheManager()
                }
            }
            return INSTANCE!!
        }
    }

    private val activityList = ArrayList<AppCompatActivity>()

    fun addActivity(activity: AppCompatActivity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity)
        }
        activityList.add(activity)
    }

    fun removeActivity(activity: AppCompatActivity) {
        activityList.remove(activity)
    }

    fun getTopActivity(): AppCompatActivity? {
        return if (activityList.size > 0) {
            activityList[activityList.size - 1]
        } else {
            null
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = activityList.size
        while (i < size) {
            activityList[i].finish()
            i++
        }
        activityList.clear()
    }

    /**
     * 应用程序完全退出
     *
     * @param context 当前上下文
     */
    @SuppressLint("NewApi")
    fun appExit(context: Context) {
        finishAllActivity()
        val activityMgr = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityMgr.killBackgroundProcesses(context.packageName)
        System.exit(0)
    }
}