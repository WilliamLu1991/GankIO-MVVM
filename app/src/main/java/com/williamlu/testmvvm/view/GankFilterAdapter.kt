package com.williamlu.testmvvm.view

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.williamlu.testmvvm.R
import com.williamlu.testmvvm.model.GankBean

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class GankFilterAdapter(data: List<GankBean>?) : BaseQuickAdapter<GankBean, BaseViewHolder>(R.layout.recycler_item_gank_data, data) {

    override fun convert(helper: BaseViewHolder?, item: GankBean) {
        with(item) {
            helper?.getView<TextView>(R.id.tv_desc)?.text = desc
            helper?.getView<TextView>(R.id.tv_who)?.text = who
            helper?.getView<TextView>(R.id.tv_date)?.text = publishedAt
        }
    }
}