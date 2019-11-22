package com.williamlu.datalib.bean

import java.io.Serializable

/**
 * @Author: WilliamLu
 * @Data: 2019/4/15
 * @Description:
 */
class BaseBean<T> : Serializable {
    var code = 200
    var msg = ""
    var data: T? = null
    var error: Boolean = false
    var results: T? = null
}