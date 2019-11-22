package com.williamlu.widgetlib

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_text_input.view.*

/**
 * 仿系统TextInputLayout输入控件，自定义图标，提示等属性
 */
class TextInputView : FrameLayout {

    val TAG="H0x"

    var hideStr=""

    @JvmOverloads
    constructor(context: Context) : super(context, null, 0) {}

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : super(context,attrs,defStyleAttr) {
        initView(context, attrs)
    }

    fun initView(context: Context, attrs: AttributeSet) {
        LayoutInflater.from(context).inflate(R.layout.view_text_input, this)
        val typeA = context.obtainStyledAttributes(attrs, R.styleable.TextInputView)
        val iconImgId = typeA.getDrawable(R.styleable.TextInputView_iconImg)
        val tipStr = typeA.getString(R.styleable.TextInputView_tipStr)
        hideStr = typeA.getString(R.styleable.TextInputView_hideStr).toString()
        val lineColor = typeA.getColor(R.styleable.TextInputView_lineColor,context.resources.getColor(R.color.color_transparent))
        setValues(iconImgId!!, tipStr!!, hideStr, lineColor)
        typeA.recycle()

        et.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Log.v(TAG,"获取到焦点...")
                moveUp()
            } else {
                Log.v(TAG,"失去焦点...")
                moveDown()
            }
        }
    }

    fun setValues(iconImg: Drawable, tipStr: String, hideStr: String, lineColor: Int) {
        if (null!= iconImg) {
            img.setImageDrawable(iconImg)
        }
        if (tipStr.isNullOrEmpty()) {
            tip_tv.text = Editable.Factory.getInstance().newEditable("")
        } else {
            tip_tv.text = Editable.Factory.getInstance().newEditable(tipStr)
        }
        if (hideStr.isNullOrEmpty()) {
            hide_tv.text = Editable.Factory.getInstance().newEditable("")
        } else {
            hide_tv.text = Editable.Factory.getInstance().newEditable(hideStr)
        }
        line.setBackgroundColor(lineColor)
    }

    fun getEtStr():String{
        return et.text.toString().trim()
    }

    fun moveUp() {
        val etStr = et.text
        if (!etStr.isNullOrEmpty()) {
            return
        }
        val up: Animation = AnimationUtils.loadAnimation(this.context, R.anim.input_up)
        up.fillAfter=true
        up.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationStart(animation: Animation?) {
                hide_tv.visibility= View.INVISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                et.hint=Editable.Factory.getInstance().newEditable(hideStr)
            }
        })
        move_ll.startAnimation(up)
    }

    fun moveDown() {
        val etStr = et.text
        if (etStr.isNullOrEmpty()) {
            et.hint = Editable.Factory.getInstance().newEditable("")
        } else {
            return
        }

        val up: Animation = AnimationUtils.loadAnimation(this.context, R.anim.input_down)
        up.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                hide_tv.visibility = View.VISIBLE
            }
        })
        move_ll.startAnimation(up)
    }

}