package com.williamlu.widgetlib.ximageview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.williamlu.widgetlib.FullSheetDialog
import com.williamlu.widgetlib.R
import java.io.IOException

/**
 * @Author: WilliamLu
 * @Date: 2019/6/11
 * @Description:
 */
object BigImageHelper {
    fun showBigImage(context: Context, picPath: String) {
        showBigImage(context, null, picPath, null)
    }

    fun showBigImage(context: Context, picRes: Int) {
        showBigImage(context, null, null, picRes)
    }

    fun showBigImage(context: Context, bitmap: Bitmap) {
        showBigImage(context, bitmap, null, null)
    }


    fun showBigImage(context: Context, bitmap: Bitmap?, picPath: String?, picRes: Int?) {
        val fullSheetDialog = FullSheetDialog(context, ViewGroup.LayoutParams.MATCH_PARENT)
        val view = View.inflate(context, R.layout.view_big_image, null)
        fullSheetDialog.setContentView(view)
        fullSheetDialog.show()

        val flClose = view.findViewById<FrameLayout>(R.id.bigimage_fl_close)
        val ivPic = view.findViewById<XImageView>(R.id.bigimage_iv_pic)

        try {
            if (bitmap != null) {
                ivPic.setImage(bitmap)
            } else if (!TextUtils.isEmpty(picPath)) {
                ivPic.setImage(picPath)
            } else if (picRes != null) {
                ivPic.setImage(BitmapFactory.decodeResource(context.resources, picRes))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //Glide.with(activity).load(picPath).into(ivPic)

        flClose.setOnClickListener {
            fullSheetDialog.dismiss()
        }
    }
}