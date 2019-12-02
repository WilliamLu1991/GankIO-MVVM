package com.williamlu.toolslib

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author: WilliamLu
 * @Date: 2019/12/2
 * @Description: recyelerview滑动距离测算工具类
 */
object RvUtil {
    /**
     * 还能向下滑动多少
     */
    fun getRemaindDistance(rv: RecyclerView): Int {
        val layoutManager = rv.layoutManager as LinearLayoutManager
        val firstVisibItem: View = rv.getChildAt(0)
        val firstItemPosition = layoutManager.findFirstVisibleItemPosition()
        val itemCount = layoutManager.itemCount
        val recycleViewHeight: Int = rv.height
        val itemHeight: Int = firstVisibItem.height
        val firstItemBottom = layoutManager.getDecoratedBottom(firstVisibItem)
        return (itemCount - firstItemPosition - 1) * itemHeight - recycleViewHeight + firstItemBottom
    }

    /**
     * 已滑动的距离
     */
    fun getAlreadyDistance(rv: RecyclerView): Int {
        val layoutManager = rv.layoutManager as LinearLayoutManager
        val firstVisibItem: View = rv.getChildAt(0)
        val firstItemPosition = layoutManager.findFirstVisibleItemPosition()
        val itemCount = layoutManager.itemCount
        val recycleViewHeight: Int = rv.height
        val itemHeight = firstVisibItem.height
        val firstItemBottom = layoutManager.getDecoratedBottom(firstVisibItem)
        return (firstItemPosition + 1) * itemHeight - firstItemBottom
    }

    /**
     * 获取RecyclerView滚动距离
     */
    fun getRvDistance(rv: RecyclerView): Int {
        val layoutManager = rv.layoutManager as LinearLayoutManager
        val firstVisibItem: View = rv.getChildAt(0)
        val firstItemPosition = layoutManager.findFirstVisibleItemPosition()
        val itemCount = layoutManager.itemCount
        val recycleViewHeight: Int = rv.height
        val itemHeight = firstVisibItem.height
        val firstItemBottom = layoutManager.getDecoratedBottom(firstVisibItem)
        return (itemCount - firstItemPosition - 1) * itemHeight - recycleViewHeight
    }
}