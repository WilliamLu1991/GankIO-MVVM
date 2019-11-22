package com.williamlu.widgetlib.pickerview

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.contrarywind.interfaces.IPickerViewData
import com.google.gson.Gson
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * @Author: WilliamLu
 * @Date: 2019-05-15
 * @Description:
 */
class PickerViewHelper {
    private var options1Items = ArrayList<ProvinceBean>()
    private var options2Items = ArrayList<ArrayList<String>>()
    private var options3Items = ArrayList<ArrayList<ArrayList<String>>>()
    private var mContext: Context? = null

    private var onPickerViewListener: OnPickerViewListener? = null

    interface OnPickerViewListener {
        fun onPickerViewConfirm(text: String)
        fun onPickerViewSelect(options1: String?, options2: String?, options3: String?)
    }

    fun showCityPickerView(pvListener: OnPickerViewListener) {
        hideSoftInput(mContext as Activity)
        CustomPickerView.getInstance().showPickerView(
            mContext,
            "城市选择",
            options1Items as ArrayList<IPickerViewData>,
            options2Items,
            options3Items
        ) { options1, options2, options3, v ->
            var tx = options1Items[options1].pickerViewText + " " +
                    options2Items[options1][options2] + " " +
                    options3Items[options1][options2][options3]
            pvListener.onPickerViewConfirm(tx)
            pvListener.onPickerViewSelect(
                options1Items[options1].pickerViewText,
                options2Items[options1][options2],
                options3Items[options1][options2][options3]
            )
        }.show()
    }

    fun initCityData(context: Context): PickerViewHelper {
        mContext = context

        val provinceData = getStringFromAssets(mContext!!, "province.json") //获取assets目录下的json文件数据
        val provinceBean = parseData(provinceData) //用Gson 转成实体
        /**
         * 添加省份数据
         */
        options1Items = provinceBean

        for (i in provinceBean.indices) { //遍历省份
            val cityList = ArrayList<String>() //该省的城市列表（第二级）
            val provinceAreaList = ArrayList<ArrayList<String>>() //该省的所有地区列表（第三极）
            for (c in 0 until provinceBean[i].city!!.size) { //遍历该省份的所有城市
                val cityName = provinceBean[i].city!![c].name
                cityList.add(cityName!!) //添加城市
                val cityAreaList = ArrayList<String>() //该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (provinceBean[i].city!![c].area == null || provinceBean[i].city!![c].area!!.size === 0) {
                    cityAreaList.add("")
                } else {
                    cityAreaList.addAll(provinceBean[i].city!![c].area!!)
                }
                provinceAreaList.add(cityAreaList) //添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList)

            /**
             * 添加地区数据
             */
            options3Items.add(provinceAreaList)
        }

        return this
    }

    private fun parseData(result: String): ArrayList<ProvinceBean> { //Gson 解析
        val detail = ArrayList<ProvinceBean>()
        try {
            val data = JSONArray(result)
            val gson = Gson()
            for (i in 0 until data.length()) {
                val entity = gson.fromJson<ProvinceBean>(
                    data.optJSONObject(i).toString(),
                    ProvinceBean::class.java!!
                )
                detail.add(entity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return detail
    }

    /**
     * 获取assets目录下的单个文件流String
     * @param context
     * @param fileName
     * @return
     */
    private fun getStringFromAssets(context: Context, fileName: String): String {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(InputStreamReader(assetManager.open(fileName)))
            var line: String
            while (true) {
                line = bf.readLine() ?: break
                stringBuilder.append(line)
            }
            bf.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }

    /**
     * 动态隐藏软键盘
     * @param activity activity
     */
    private fun hideSoftInput(activity: Activity) {
        val view = activity.window.peekDecorView()
        if (view != null) {
            val inputmanger = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputmanger.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}