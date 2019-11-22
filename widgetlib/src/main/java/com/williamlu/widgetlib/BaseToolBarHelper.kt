package com.williamlu.widgetlib

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

/**
 * @Author: WilliamLu
 * @Data: 2018/12/4
 * @Description:
 */
class BaseToolBarHelper(baseToolbar: Toolbar) {
    private var mBaseToolbar: Toolbar? = null
    private var mToolbarFlLeft: FrameLayout? = null
    private var mToolbarIvLeft: ImageView? = null
    private var mToolbarFlRight1: FrameLayout? = null
    private var mToolbarFlRight2: FrameLayout? = null
    private var mToolbarIvRight1: ImageView? = null
    private var mToolbarIvRight2: ImageView? = null
    private var mToolbarTvRight: TextView? = null
    private var mToolbarTvTitle: TextView? = null
    private var mToolbarLlSearch: LinearLayout? = null
    private var mToolbarEtSearchContent: TextView? = null
    private var mToolbarTvSearchConfirm: TextView? = null

    init {
        mBaseToolbar = baseToolbar
        if (mBaseToolbar != null) {
            mToolbarFlLeft = mBaseToolbar!!.findViewById<FrameLayout>(R.id.toolbar_fl_left)
            mToolbarIvLeft = mBaseToolbar!!.findViewById<ImageView>(R.id.toolbar_iv_left)
            mToolbarFlRight1 = mBaseToolbar!!.findViewById<FrameLayout>(R.id.toolbar_fl_right1)
            mToolbarIvRight1 = mBaseToolbar!!.findViewById<ImageView>(R.id.toolbar_iv_right1)
            mToolbarFlRight2 = mBaseToolbar!!.findViewById<FrameLayout>(R.id.toolbar_fl_right2)
            mToolbarIvRight2 = mBaseToolbar!!.findViewById<ImageView>(R.id.toolbar_iv_right2)
            mToolbarTvRight = mBaseToolbar!!.findViewById<TextView>(R.id.toolbar_tv_right)
            mToolbarTvTitle = mBaseToolbar!!.findViewById<TextView>(R.id.toolbar_tv_title)
            mToolbarLlSearch = mBaseToolbar!!.findViewById<LinearLayout>(R.id.toolbar_ll_search)
            mToolbarEtSearchContent = mBaseToolbar!!.findViewById<TextView>(R.id.toolbar_et_search_content)
            mToolbarTvSearchConfirm = mBaseToolbar!!.findViewById<TextView>(R.id.toolbar_tv_search_confirm)
        }
    }

    /*companion object {
        private var INSTANCE: BaseToolBarHelper? = null

        fun getInstance(baseToolbar: Toolbar): BaseToolBarHelper {
            synchronized(BaseToolBarHelper::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = BaseToolBarHelper(baseToolbar)
                }
            }
            return INSTANCE!!
        }
    }*/

    fun showLeftView(): BaseToolBarHelper {
        mToolbarFlLeft!!.visibility = View.VISIBLE
        return this
    }

    fun showRight1View(): BaseToolBarHelper {
        mToolbarFlRight1!!.visibility = View.VISIBLE
        return this
    }

    fun showRight2View(): BaseToolBarHelper {
        mToolbarFlRight2!!.visibility = View.VISIBLE
        return this
    }

    fun showRightTvView(): BaseToolBarHelper {
        mToolbarTvRight!!.visibility = View.VISIBLE
        return this
    }

    fun showSearchView(): BaseToolBarHelper {
        mToolbarFlRight1!!.visibility = View.GONE
        mToolbarFlRight2!!.visibility = View.GONE
        mToolbarTvRight!!.visibility = View.GONE
        mToolbarLlSearch!!.visibility = View.VISIBLE
        mToolbarEtSearchContent!!.visibility = View.VISIBLE
        mToolbarTvSearchConfirm!!.visibility = View.GONE
        return this
    }

    fun dismissLeftView(): BaseToolBarHelper {
        mToolbarFlLeft!!.visibility = View.GONE
        return this
    }

    fun dismissRight1View(): BaseToolBarHelper {
        mToolbarFlRight1!!.visibility = View.GONE
        return this
    }

    fun dismissRight2View(): BaseToolBarHelper {
        mToolbarFlRight2!!.visibility = View.GONE
        return this
    }

    fun dismissRightTvView(): BaseToolBarHelper {
        mToolbarTvRight!!.visibility = View.GONE
        return this
    }

    fun dismissSearchView(): BaseToolBarHelper {
        mToolbarLlSearch!!.visibility = View.GONE
        mToolbarEtSearchContent!!.visibility = View.GONE
        mToolbarTvSearchConfirm!!.visibility = View.GONE
        return this
    }

    fun dismissAllRightView(): BaseToolBarHelper {
        mToolbarFlRight1!!.visibility = View.GONE
        mToolbarFlRight2!!.visibility = View.GONE
        mToolbarTvRight!!.visibility = View.GONE
        return this
    }

    fun dismissAllView(): BaseToolBarHelper {
        mToolbarFlLeft!!.visibility = View.GONE
        mToolbarFlRight1!!.visibility = View.GONE
        mToolbarFlRight2!!.visibility = View.GONE
        mToolbarTvRight!!.visibility = View.GONE
        return this
    }

    fun setBgImg(leftResId: Int): BaseToolBarHelper {
        setBgImg(leftResId, -1)
        return this
    }

    fun setBgImg(leftResId: Int, right1ResId: Int): BaseToolBarHelper {
        setBgImg(leftResId, right1ResId, -1)
        return this
    }

    fun setBgImg(leftResId: Int, right1ResId: Int, right2ResId: Int): BaseToolBarHelper {
        if (leftResId != -1) {
            mToolbarIvLeft!!.setBackgroundResource(leftResId)
            mToolbarFlLeft!!.visibility = View.VISIBLE
        }
        if (right1ResId != -1) {
            mToolbarIvRight1!!.setBackgroundResource(right1ResId)
            mToolbarFlRight1!!.visibility = View.VISIBLE
        }
        if (right2ResId != -1) {
            mToolbarIvRight2!!.setBackgroundResource(right2ResId)
            mToolbarFlRight2!!.visibility = View.VISIBLE
        }
        return this
    }

    fun setTitleColor(colorId: Int): BaseToolBarHelper {
        mToolbarTvTitle!!.setTextColor(colorId)
        return this
    }

    fun setToolbarBgColor(colorId: Int): BaseToolBarHelper {
        mBaseToolbar!!.setBackgroundColor(colorId)
        return this
    }

    fun setToolbarBgRes(resId: Int): BaseToolBarHelper {
        mBaseToolbar!!.setBackgroundResource(resId)
        return this
    }

    fun setTitleName(name: String): BaseToolBarHelper {
        mToolbarTvTitle!!.text = name
        return this
    }

    fun setRightTvName(name: String): BaseToolBarHelper {
        mToolbarTvRight!!.text = name
        mToolbarTvRight!!.visibility = View.VISIBLE
        return this
    }

    fun getTitleView(): TextView {
        return mToolbarTvTitle!!
    }

    fun getLeftView(): FrameLayout {
        return mToolbarFlLeft!!
    }

    fun getRight1View(): FrameLayout {
        return mToolbarFlRight1!!
    }

    fun getRight2View(): FrameLayout {
        return mToolbarFlRight2!!
    }

    fun getRightTvView(): TextView {
        return mToolbarTvRight!!
    }

    fun getEtSearchContentView(): TextView {
        return mToolbarEtSearchContent!!
    }

    fun getSearchConfirmView(): TextView {
        return mToolbarTvSearchConfirm!!
    }

}