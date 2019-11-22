package com.williamlu.widgetlib.pickerview

import com.contrarywind.interfaces.IPickerViewData
import java.io.Serializable

/**
 * @Author: WilliamLu
 * @Data: 2018/12/29
 * @Description:
 */
class ProvinceBean : Serializable, IPickerViewData {
    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    var name: String? = null
    var city: List<CityBean>? = null

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。c
    override fun getPickerViewText(): String? {
        return this.name
    }

    class CityBean : Serializable {

        /**
         * name : 城市
         * area : ["东城区","西城区","崇文区","昌平区"]
         */

        var name: String? = null
        var area: List<String>? = null
    }
}