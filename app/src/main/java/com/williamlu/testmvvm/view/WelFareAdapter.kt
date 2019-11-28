package com.williamlu.testmvvm.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.williamlu.testmvvm.R
import com.williamlu.testmvvm.databinding.ItemWelfareBinding
import com.williamlu.testmvvm.model.GankBean

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class WelFareAdapter(data: List<GankBean>?) : BaseQuickAdapter<GankBean, WelFareViewHolder>(data) {

    override fun createBaseViewHolder(parent: ViewGroup?, layoutResId: Int): WelFareViewHolder {
        val itemWelfareBinding =
            DataBindingUtil.inflate<ItemWelfareBinding>(LayoutInflater.from(mContext), R.layout.item_welfare, parent, false)
        return WelFareViewHolder(itemWelfareBinding)
    }

    override fun convert(helper: WelFareViewHolder?, item: GankBean) {
        helper?.dataBinding?.welfarebean = item
        helper?.dataBinding?.executePendingBindings()
    }
}