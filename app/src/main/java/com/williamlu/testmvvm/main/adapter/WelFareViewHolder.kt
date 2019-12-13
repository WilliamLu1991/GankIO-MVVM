package com.williamlu.testmvvm.main.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.williamlu.testmvvm.databinding.ItemGankDataBinding
import com.williamlu.testmvvm.databinding.ItemWelfareBinding

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class WelFareViewHolder(itemWelfareBinding: ItemWelfareBinding) : BaseViewHolder(itemWelfareBinding.root) {
    var dataBinding: ItemWelfareBinding = itemWelfareBinding
}