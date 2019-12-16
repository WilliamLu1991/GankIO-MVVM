package com.williamlu.testmvvm.base

/**
 * @Author: WilliamLu
 * @Date: 2018/11/23
 * @Description:
 */
class BaseServerController<T>(code: Int, data: T?, errorMsg: String? = null) {

    var code: Int = 0
    var data: T? = null
    var errorMsg: String? = null

    init {
        this.code = code
        this.data = data
        this.errorMsg = errorMsg
    }

    companion object {
        const val SUCCESS = 1
        const val ERROR = 0
        /**
         * 获取数据成功
         */
        fun <T> onSuccess(data: T?) = BaseServerController(SUCCESS, data)

        /**
         * 操作数据完成
         */
        //fun onCompleted()

        /**
         * 获取数据失败
         */
        fun <T> onError(msg: String?, data: T? = null) = BaseServerController(ERROR, data, msg)
    }
}