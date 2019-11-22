package com.williamlu.testmvvm.view

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.williamlu.testmvvm.R
import com.williamlu.testmvvm.model.GankBean

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class WelFareAdapter(data: List<GankBean>?) : BaseQuickAdapter<GankBean, BaseViewHolder>(R.layout.recycler_item_welfare, data) {

    override fun convert(helper: BaseViewHolder?, item: GankBean) {
        with(item) {
            Glide.with(mContext)
                .applyDefaultRequestOptions(RequestOptions().apply {
                    centerCrop()
                })
                .load(url)
                .into(helper!!.getView(R.id.iv_welfare))
        }
    }
}