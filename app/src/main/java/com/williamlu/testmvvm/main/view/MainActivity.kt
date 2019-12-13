package com.williamlu.testmvvm.main.view

import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.williamlu.testmvvm.R
import com.williamlu.testmvvm.base.AppBaseActivity
import com.williamlu.testmvvm.databinding.ActivityMainBinding
import com.williamlu.testmvvm.model.GankBean
import com.williamlu.testmvvm.model.GankFilterType
import com.williamlu.testmvvm.main.adapter.GankFilterAdapter
import com.williamlu.testmvvm.main.adapter.WelFareAdapter
import com.williamlu.testmvvm.main.viewmodel.MainVM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_app_bar.*
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppBaseActivity<MainVM, ActivityMainBinding>(), BaseQuickAdapter.RequestLoadMoreListener {
    private var mPage: Int = 1
    private var mCurrentFilter: String = GankFilterType.ANDROID
    private var mGankFilterAdapter: GankFilterAdapter? = null
    private var mWelFareAdapter: WelFareAdapter? = null

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        showTranslucentBar()
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }
        setupDrawerLayout()
        showLoadingView()
        mPage = 1
        getRvData(GankFilterType.ANDROID, mPage)
    }

    private fun setupDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.drawerArrowDrawable.color = Color.BLACK
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener { menuItem ->
            title = menuItem.title
            drawer_layout.closeDrawers()
            mPage = 1
            showSwipeRl()
            when (menuItem.itemId) {
                /*R.id.menu_today -> {
                    getRvData(GankFilterType.TODAY)
                }*/
                R.id.menu_android -> {
                    getRvData(GankFilterType.ANDROID, mPage)
                }
                R.id.menu_ios -> {
                    getRvData(GankFilterType.IOS, mPage)
                }
                R.id.menu_web -> {
                    getRvData(GankFilterType.WEB, mPage)
                }
                R.id.menu_app -> {
                    getRvData(GankFilterType.APP, mPage)
                }
                R.id.menu_extra -> {
                    getRvData(GankFilterType.EXTRA_SOURCES, mPage)
                }
                /*R.id.menu_video -> {
                    filterChange(GankFilterType.VIDEO)
                }*/
                R.id.menu_welfare -> {
                    getRvData(GankFilterType.WELFARE, mPage)
                }
                R.id.menu_about -> {

                }
            }
            true
        }

        Glide.with(this)
            .applyDefaultRequestOptions(RequestOptions().apply { centerCrop() })
            .load(R.drawable.seb5)
            .into(nav_view.getHeaderView(0).iv_nav_header)
    }

    private fun getRvData(gankFilterType: String, page: Int) {
        mCurrentFilter = gankFilterType
        mViewModel?.getGankFilter(gankFilterType, page)?.observe(this, Observer {
            dismissAllView()
            if (gankFilterType == GankFilterType.WELFARE) {
                initWelFareRv(it)
            } else {
                initFilterRv(it)
            }
        })
    }

    private fun initWelFareRv(it: List<GankBean>?) {
        if (it == null) {
            showEmptyDataView()
            return
        }
        mBinding?.mainRv?.visibility = View.GONE
        mBinding?.mainRvWelfare?.visibility = View.VISIBLE
        if (mWelFareAdapter == null) {
            mBinding?.mainRvWelfare?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            mWelFareAdapter = WelFareAdapter(it)
            mBinding?.mainRvWelfare?.adapter = mWelFareAdapter
            mWelFareAdapter?.setOnLoadMoreListener(this)
        } else {
            if (mPage == 1) {
                mWelFareAdapter?.setNewData(it)
            } else {
                if (it.isNotEmpty()) {
                    mWelFareAdapter?.addData(it)
                    mWelFareAdapter?.loadMoreComplete()
                } else {
                    mWelFareAdapter?.loadMoreEnd()
                }
            }
        }
    }

    private fun initFilterRv(it: List<GankBean>?) {
        if (it == null) {
            showEmptyDataView()
            return
        }
        mBinding?.mainRv?.visibility = View.VISIBLE
        mBinding?.mainRvWelfare?.visibility = View.GONE
        if (mGankFilterAdapter == null) {
            mBinding?.mainRv?.layoutManager = LinearLayoutManager(this)
            mGankFilterAdapter = GankFilterAdapter(it)
            mBinding?.mainRv?.adapter = mGankFilterAdapter
            mGankFilterAdapter?.setOnLoadMoreListener(this)
        } else {
            if (mPage == 1) {
                mGankFilterAdapter?.setNewData(it)
            } else {
                if (it.isNotEmpty()) {
                    mGankFilterAdapter?.addData(it)
                    mGankFilterAdapter?.loadMoreComplete()
                } else {
                    mGankFilterAdapter?.loadMoreEnd()
                }
            }
        }
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.search -> {
                //SearchActivity.start(this)
                return true
            }
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun initListener() {
        mSwipeRl.setOnRefreshListener {
            mPage = 1
            getRvData(mCurrentFilter, mPage)
        }
    }

    override fun onLoadMoreRequested() {
        mPage += 1
        getRvData(mCurrentFilter, mPage)
    }

}
