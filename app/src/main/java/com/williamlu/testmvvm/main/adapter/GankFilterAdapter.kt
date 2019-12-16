package com.williamlu.testmvvm.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.williamlu.testmvvm.R
import com.williamlu.testmvvm.databinding.ItemGankDataBinding
import com.williamlu.testmvvm.model.GankBean

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class GankFilterAdapter(data: List<GankBean>?) : BaseQuickAdapter<GankBean, GankFilterAdapter.GankFilterViewHolder>(data) {

    override fun createBaseViewHolder(parent: ViewGroup?, layoutResId: Int): GankFilterViewHolder {
        val itemGankDataBinding =
            DataBindingUtil.inflate<ItemGankDataBinding>(LayoutInflater.from(mContext), R.layout.item_gank_data, parent, false)
        return GankFilterViewHolder(itemGankDataBinding)
    }

    override fun convert(helper: GankFilterViewHolder?, item: GankBean?) {
        helper?.dataBinding?.gankbean = item
        helper?.dataBinding?.executePendingBindings()
    }

    class GankFilterViewHolder(itemGankDataBinding: ItemGankDataBinding) : BaseViewHolder(itemGankDataBinding.root) {
        var dataBinding: ItemGankDataBinding = itemGankDataBinding
    }
}