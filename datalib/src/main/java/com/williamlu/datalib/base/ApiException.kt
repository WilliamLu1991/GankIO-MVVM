package com.williamlu.datalib.base

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class ApiException(errorCode: Int, errorMsg: String) : IllegalArgumentException() {
    var errorCode = errorCode
    var errorMsg = errorMsg
}