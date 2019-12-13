package com.williamlu.testmvvm.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * @Author: WilliamLu
 * @Date: 2019/11/26
 * @Description:
 */
object BindingAdapterUtil {
    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun glideImageUrl(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url).into(imageView)
    }
}