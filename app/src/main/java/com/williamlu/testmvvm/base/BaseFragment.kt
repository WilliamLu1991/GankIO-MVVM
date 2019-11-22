package com.williamlu.testmvvm.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.williamlu.testmvvm.AppConstant
import com.williamlu.testmvvm.R
import com.williamlu.toolslib.SpUtils
import com.williamlu.widgetlib.BaseToolBarHelper
import com.williamlu.widgetlib.statusbar.StatusBarCompat
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
abstract class BaseFragment : Fragment(), BaseLoadView {
    private val mSpUtils = SpUtils.getInstance(AppConstant.SpConstant.USER_INFO)
    private var rootView: View? = null
    private var mLoadingDialog: Dialog? = null
    private var mLayoutEmptyLoading: RelativeLayout? = null
    protected var mLayoutLlEmptyData: LinearLayout? = null
    private var mLayoutLlLoading: LinearLayout? = null
    private var mLayoutTvEmptyDes: TextView? = null
    private var mLayoutIvLoading: ImageView? = null
    private var mLayoutIvEmpty: ImageView? = null
    protected var mLayoutLlError: LinearLayout? = null
    protected var mBaseToolbar: Toolbar? = null
    protected var mBaseToolBarHelper: BaseToolBarHelper? = null
    private var mSwipeRl: SwipeRefreshLayout? = null
    private var mCompositeDisposable: CompositeDisposable? = null
    private var mActivity: BaseActivity<*,*>? = null
    private var isViewInit = false
    private var isViewVisiable = false

    /**
     * 获取布局ID
     */
    protected abstract fun getContentViewLayoutID(): Int

    /**
     * 初始化布局以及View控件
     */
    protected abstract fun initView(rootView: View?)

    /**
     * 初始化监听事件
     */
    protected abstract fun initListener()

    /**
     * 懒加载实时刷新fragment数据，管理多个fragment时使用
     */
    open fun loadFragmentData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity<*,*>?
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            if (getContentViewLayoutID() != 0) {
                rootView = inflater.inflate(getContentViewLayoutID(), container, false)
            }
        }
        val parent = rootView?.parent as ViewGroup?
        parent?.removeView(rootView)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!isViewInit) {
            setContentView(rootView)
            showWhiteBar()
            initView(rootView)
            initListener()
            isViewInit = true
            isCanLoadData()
        }
        isViewInit = true

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isViewVisiable = isVisibleToUser
        isCanLoadData()
    }

    private fun isCanLoadData() {
        if (isViewVisiable && isViewInit) {
            loadFragmentData()
        }
    }

    fun setContentView(v: View?) {
        mLayoutEmptyLoading = v?.findViewById<RelativeLayout>(R.id.layout_empty_loading)
        mLayoutLlEmptyData = v?.findViewById<LinearLayout>(R.id.layout_ll_empty_data)
        mLayoutLlLoading = v?.findViewById<LinearLayout>(R.id.layout_ll_loading)
        mLayoutIvLoading = v?.findViewById<ImageView>(R.id.layout_iv_loading)
        mLayoutTvEmptyDes = v?.findViewById<TextView>(R.id.layout_empty_data_tv)
        mLayoutIvEmpty = v?.findViewById<ImageView>(R.id.layout_empty_data_iv)
        mLayoutLlError = v?.findViewById<LinearLayout>(R.id.layout_ll_error)
        mBaseToolbar = v?.findViewById<Toolbar>(R.id.base_toolbar)
        if (mBaseToolbar != null) {
            mBaseToolBarHelper = BaseToolBarHelper(mBaseToolbar!!)
        }
        mSwipeRl = v?.findViewById<SwipeRefreshLayout>(R.id.mSwipeRl)
        if (mSwipeRl != null) {
            mSwipeRl?.setColorSchemeResources(R.color.colorAccent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearSubscribe()
        EventBus.getDefault().unregister(this)
    }

    /**
     * 统一管理Disposable 添加和清空订阅
     */
    protected fun addSubscribe(disposable: Disposable?) {
        if (disposable == null) return
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable)
    }

    protected fun removeSubscribe(disposable: Disposable?) {
        if (disposable == null) return
        if (mCompositeDisposable != null) {
            mCompositeDisposable?.remove(disposable)
        }
    }

    protected fun clearSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable?.clear()
            mCompositeDisposable = null
        }
    }

    /**
     * 基础dialog及view的统一管理
     */
    override fun showLoadingDialog() {
        mActivity?.showLoadingDialog()
    }

    override fun dismissLoadingDialog() {
        mActivity?.dismissLoadingDialog()
    }

    override fun showLoadingView() {
        if (mLayoutEmptyLoading != null) {
            //Glide.with(this).load(R.drawable.gif_loading).into(mLayoutIvLoading!!)
            mLayoutEmptyLoading?.visibility = View.VISIBLE
            mLayoutLlLoading?.visibility = View.VISIBLE
            mLayoutLlEmptyData?.visibility = View.GONE
            mLayoutLlError?.visibility = View.GONE
        }
    }

    override fun showEmptyDataView() {
        if (mLayoutEmptyLoading != null) {
            mLayoutEmptyLoading?.visibility = View.VISIBLE
            mLayoutLlLoading?.visibility = View.GONE
            mLayoutLlEmptyData?.visibility = View.VISIBLE
            mLayoutLlError?.visibility = View.GONE
        }
    }

    override fun showErrorView() {
        if (mLayoutEmptyLoading != null) {
            mLayoutEmptyLoading?.visibility = View.VISIBLE
            mLayoutLlLoading?.visibility = View.GONE
            mLayoutLlEmptyData?.visibility = View.GONE
            mLayoutLlError?.visibility = View.VISIBLE
        }
    }

    override fun showEmptyDataDesView(des: String) {
        if (mLayoutEmptyLoading != null) {
            mLayoutEmptyLoading?.visibility = View.VISIBLE
            mLayoutLlLoading?.visibility = View.GONE
            mLayoutLlEmptyData?.visibility = View.VISIBLE
            mLayoutLlError?.visibility = View.GONE
            mLayoutTvEmptyDes?.text = des
        }
    }

    override fun showEmptyDataDesView(des: String, resPic: Int) {
        if (mLayoutEmptyLoading != null) {
            mLayoutEmptyLoading?.visibility = View.VISIBLE
            mLayoutLlLoading?.visibility = View.GONE
            mLayoutLlEmptyData?.visibility = View.VISIBLE
            mLayoutLlError?.visibility = View.GONE
            mLayoutTvEmptyDes?.text = des
            mLayoutIvEmpty?.setBackgroundResource(resPic)
        }
    }

    override fun dismissAllView() {
        mLayoutEmptyLoading?.visibility = View.GONE
        mLayoutLlLoading?.visibility = View.GONE
        mLayoutLlEmptyData?.visibility = View.GONE
        mLayoutLlError?.visibility = View.GONE
        dismissSwipeRlAndLoadingDialog()
    }

    override fun showSwipeRl() {
        if (mSwipeRl != null && !mSwipeRl!!.isRefreshing) {
            mSwipeRl?.isRefreshing = true
        }
    }

    override fun dismissSwipeRl() {
        if (mSwipeRl != null && mSwipeRl!!.isRefreshing) {
            mSwipeRl?.isRefreshing = false
        }
    }

    override fun dismissSwipeRlAndLoadingDialog() {
        dismissLoadingDialog()
        dismissSwipeRl()
    }

    /**
     * 展示沉浸式白色状态栏和toolbar
     */
    private fun showWhiteBar() {
        StatusBarCompat.setStatusBarColor(activity!!, resources.getColor(R.color.color_ffffff))
        StatusBarCompat.changeToLightStatusBar(activity!!)
        mBaseToolBarHelper?.setToolbarBgRes(R.color.color_ffffff)
        mBaseToolBarHelper?.getTitleView()?.setTextColor(resources.getColor(R.color.color_333333))
        mBaseToolBarHelper?.getRightTvView()?.setTextColor(resources.getColor(R.color.color_333333))
    }

}