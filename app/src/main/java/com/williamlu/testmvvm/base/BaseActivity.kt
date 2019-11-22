package com.williamlu.testmvvm.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.williamlu.testmvvm.AppConstant
import com.williamlu.toolslib.PermissionsUtils
import com.williamlu.toolslib.SpUtils
import com.williamlu.widgetlib.BaseToolBarHelper
import com.williamlu.widgetlib.CustomLoadingDialog
import com.williamlu.widgetlib.statusbar.StatusBarCompat
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.ParameterizedType


/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
abstract class BaseActivity<VM : BaseViewModel, VDB : ViewDataBinding> : AppCompatActivity(), BaseLoadView {
    private var mLoadingDialog: Dialog? = null
    protected var mLayoutEmptyLoading: RelativeLayout? = null
    private var mLayoutLlEmptyData: LinearLayout? = null
    protected var mLayoutLlLoading: LinearLayout? = null
    private var mLayoutIvLoading: ImageView? = null
    private var mLayoutIvEmpty: ImageView? = null
    private var mLayoutTvEmptyDes: TextView? = null
    private var mLayoutLlError: LinearLayout? = null
    protected var mBaseToolbar: Toolbar? = null
    private var mActivityCacheManager: ActivityCacheManager? = null
    private var mSwipeRl: SwipeRefreshLayout? = null
    protected var mBaseToolBarHelper: BaseToolBarHelper? = null
    private var mCompositeDisposable: CompositeDisposable? = null
    protected var mPermissions: Array<String>? = null
    protected var mViewModel: VM? = null
    protected var binding: VDB? = null

    /**
     * 获取布局ID
     */
    protected abstract fun getContentViewLayoutID(): Int

    /**
     * 初始化布局以及View控件
     */
    protected abstract fun initView()

    /**
     * 初始化监听事件
     */
    protected abstract fun initListener()

    /**
     * 初始化跳转数据
     */
    protected abstract fun onInitParams(bundle: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mActivityCacheManager == null) {
            mActivityCacheManager = ActivityCacheManager.getInstance()
        }
        mActivityCacheManager?.addActivity(this)
        if (mLoadingDialog == null) {
            mLoadingDialog = CustomLoadingDialog.getInstance().createLoadingDialog(this, AppConstant.DialogConstant.LOADING)
        }
        //EventBus.getDefault().register(this)
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID())
            if (intent != null && intent.extras != null) {
                onInitParams(intent.extras)
            } else {
                onInitParams(Bundle())
            }

            binding = DataBindingUtil.setContentView(this, getContentViewLayoutID())
            binding?.lifecycleOwner = this
            createViewModel()

            //是否要检查权限
            /*if (isCheckPermission()) {
                PermissionsUtils.getInstance()
                    .checkAndRequestPermissions(this, mPermissions!!, object : PermissionsUtils.IPermissionsResult {
                        override fun passPermissions() {
                            //权限通过
                            /*initPresenter()
                            initView()
                            initListener()*/
                        }

                        override fun forbidPermissions() {
                            //权限不通过
                            //finish()
                        }
                    })
            } else {
                initPresenter()
                initView()
                initListener()
            }*/

            showWhiteBar()
            initView()
            initListener()
        }
    }

    fun createViewModel() {
        if (mViewModel == null) {
            val modelClass: Class<VM>
            val type = javaClass.genericSuperclass
            if (type is ParameterizedType) {
                modelClass = type.actualTypeArguments[0] as Class<VM>
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel::class.java as Class<VM>
            }
            mViewModel = ViewModelProviders.of(this).get(modelClass)
        }
    }


    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        mLayoutEmptyLoading = findViewById<RelativeLayout>(com.williamlu.testmvvm.R.id.layout_empty_loading)
        mLayoutLlEmptyData = findViewById<LinearLayout>(com.williamlu.testmvvm.R.id.layout_ll_empty_data)
        mLayoutLlLoading = findViewById<LinearLayout>(com.williamlu.testmvvm.R.id.layout_ll_loading)
        mLayoutIvLoading = findViewById<ImageView>(com.williamlu.testmvvm.R.id.layout_iv_loading)
        mLayoutTvEmptyDes = findViewById<TextView>(com.williamlu.testmvvm.R.id.layout_empty_data_tv)
        mLayoutIvEmpty = findViewById<ImageView>(com.williamlu.testmvvm.R.id.layout_empty_data_iv)
        mLayoutLlError = findViewById<LinearLayout>(com.williamlu.testmvvm.R.id.layout_ll_error)
        mBaseToolbar = findViewById<Toolbar>(com.williamlu.testmvvm.R.id.base_toolbar)
        mSwipeRl = findViewById<SwipeRefreshLayout>(com.williamlu.testmvvm.R.id.mSwipeRl)
        if (mBaseToolbar != null) {
            setSupportActionBar(mBaseToolbar)
            mBaseToolBarHelper = BaseToolBarHelper(mBaseToolbar!!)
        }
        if (mSwipeRl != null) {
            mSwipeRl?.setColorSchemeResources(com.williamlu.testmvvm.R.color.colorAccent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearSubscribe()
        mActivityCacheManager?.removeActivity(this)
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
     * 检查权限及申请
     * 通过 mPermissions 设置要申请的权限
     */
    protected abstract fun checkPermission(): Boolean

    private fun isCheckPermission(): Boolean {
        return checkPermission()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    /**
     * 基础dialog及view的统一管理
     */
    override fun showLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog?.show()
        }
    }

    override fun dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog?.dismiss()
        }
    }

    override fun showLoadingView() {
        if (mLayoutEmptyLoading != null) {
            //Glide.with(this).load(R.drawable.gif_loading).into(mLayoutIvLoading?)
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
            mLayoutLlError?.visibility = View.GONE
            mLayoutLlEmptyData?.visibility = View.VISIBLE
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
        //StatusBarCompat.translucentStatusBar(this, true)
        StatusBarCompat.setStatusBarColor(this, resources.getColor(com.williamlu.testmvvm.R.color.color_ffffff))
        StatusBarCompat.changeToLightStatusBar(this)
        mBaseToolBarHelper?.setToolbarBgRes(com.williamlu.testmvvm.R.color.color_ffffff)
        mBaseToolBarHelper?.getTitleView()?.setTextColor(resources.getColor(com.williamlu.testmvvm.R.color.color_333333))
        mBaseToolBarHelper?.getRightTvView()?.setTextColor(resources.getColor(com.williamlu.testmvvm.R.color.color_333333))
    }
}