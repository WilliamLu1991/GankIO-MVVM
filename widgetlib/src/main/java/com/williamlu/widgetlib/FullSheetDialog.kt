package com.williamlu.widgetlib

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * @Author: WilliamLu
 * @Data: 2018/12/13
 * @Description:
 */
class FullSheetDialog(context: Context, var height: Int = 0) : BottomSheetDialog(context) {
    override fun show() {
        super.show()
        var layout = window!!.decorView.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        layout.setBackgroundResource(R.drawable.shape_bg_ffffff_top_r10)
        var orginLayoutParams = layout.layoutParams
        if (height == 0) {
            orginLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            orginLayoutParams.height = height
        }
        layout.layoutParams = orginLayoutParams
        val mDialogBehavior = BottomSheetBehavior.from(layout)
        mDialogBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}
