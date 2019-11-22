package com.williamlu.testmvvm.base

import com.williamlu.datalib.bean.BaseBean

/**
 * @Author: WilliamLu
 * @Date: 2018/11/23
 * @Description:
 */
class BaseServerController<T>(data: T?) {

    private var bsv: BaseServerView<T>? = null
    private var data: T? = null

    init {
        this.data = data
    }

    fun setBaseServerView(bsv: BaseServerView<T>) {
        this.bsv = bsv
    }

    interface BaseServerView<T> {
        /**
         * 获取数据成功
         */
        fun onSuccess(data: T?)

        /**
         * 操作数据完成
         */
        //fun onCompleted()

        /**
         * 获取数据失败
         */
        fun onError(msg: String?)
    }

    fun onSuccess(): BaseServerController<T>? {
        bsv?.onSuccess(data)
        return BaseServerController(data)
    }

    /*fun onCompleted(): BaseServerController<T> {
        bsv?.onCompleted()
        return this
    }*/

    fun onError(msg: String?): BaseServerController<T>? {
        bsv?.onError(msg)
        return BaseServerController(data)
    }
}