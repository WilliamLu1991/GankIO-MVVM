package com.williamlu.testmvvm.main.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.williamlu.testmvvm.databinding.ItemGankDataBinding

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class GankFilterViewHolder(itemGankDataBinding: ItemGankDataBinding) : BaseViewHolder(itemGankDataBinding.root) {
    var dataBinding: ItemGankDataBinding = itemGankDataBinding
}