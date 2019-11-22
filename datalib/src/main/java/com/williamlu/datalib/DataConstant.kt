package com.williamlu.datalib

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
class DataConstant {

    object ToastConstant {
        const val ERROR_NETWORK = "网络服务异常，请检查网络或重新加载！"
        const val ERROR_TIMEOUT = "网络超时，请稍后再试！"
        const val ERROR_JSONSYNTAX = "数据解析异常！"
        const val ERROR_SERVER = "网络请求异常！"
        const val ERROR_DATA = "数据异常！"
        const val ERROR_URL_OR_PARAMETER = "请求的地址不存在或者包含不支持的参数!"
        const val ERROR_NO_AUTHORIZE = "未授权!"
        const val ERROR_NO_ACCESS = "被禁止访问!"
        const val ERROR_RESOURCE_NO_EXIST = "请求的资源不存在!"
        const val ERROR_INSIDE = "内部错误！"
        const val ERROR_SERVER_TOKEN = "登录失效，请重新登录"
    }

}