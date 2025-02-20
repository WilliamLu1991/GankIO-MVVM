package com.williamlu.datalib.base

import com.google.gson.JsonSyntaxException
import com.orhanobut.logger.Logger
import com.williamlu.datalib.DataConstant
import com.williamlu.datalib.bean.ApiExceptionEvent
import com.williamlu.datalib.bean.ServerExceptionEvent
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
abstract class ApiObserver<T> : Observer<T> {
    private var mDisposable: Disposable? = null

    override fun onSubscribe(d: Disposable) {
        mDisposable = d
    }

    override fun onError(e: Throwable) {
        var showMsg = "网络请求异常"
        if (e is ApiException) {
            //处理服务器返回的错误
            showMsg = e.errorMsg
            EventBus.getDefault().post(ApiExceptionEvent(e.errorCode, e.errorMsg))
        } else if (e is ConnectException || e is UnknownHostException) {
            showMsg = DataConstant.ToastConstant.ERROR_NETWORK
            EventBus.getDefault().post(ServerExceptionEvent(showMsg))
        } else if (e is TimeoutException || e is SocketTimeoutException) {
            showMsg = DataConstant.ToastConstant.ERROR_TIMEOUT
            EventBus.getDefault().post(ServerExceptionEvent(showMsg))
        } else if (e is JsonSyntaxException) {
            showMsg = DataConstant.ToastConstant.ERROR_JSONSYNTAX
            EventBus.getDefault().post(ServerExceptionEvent(showMsg))
        } else if (e is HttpException) {
            if (e.code() == 400) {
                showMsg = DataConstant.ToastConstant.ERROR_URL_OR_PARAMETER
            } else if (e.code() == 401) {
                showMsg = DataConstant.ToastConstant.ERROR_NO_AUTHORIZE
            } else if (e.code() == 403) {
                showMsg = DataConstant.ToastConstant.ERROR_NO_ACCESS
            } else if (e.code() == 404) {
                showMsg = DataConstant.ToastConstant.ERROR_RESOURCE_NO_EXIST
            } else if (e.code() == 500) {
                showMsg = DataConstant.ToastConstant.ERROR_INSIDE
            }
            EventBus.getDefault().post(ServerExceptionEvent(showMsg))
        } else {
            showMsg = DataConstant.ToastConstant.ERROR_SERVER
            EventBus.getDefault().post(ServerExceptionEvent(showMsg))
        }
        Logger.e("Error：$e,Msg：$showMsg")
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
        }

    }

    override fun onComplete() {
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
        }
    }
}