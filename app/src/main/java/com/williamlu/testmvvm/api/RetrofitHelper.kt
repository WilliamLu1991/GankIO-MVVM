package com.williamlu.testmvvm.api

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import com.williamlu.datalib.base.LogInterceptor
import com.williamlu.datalib.base.XInterceptor
import com.williamlu.toolslib.GlobalCache
import com.williamlu.toolslib.SpUtils
import com.williamlu.testmvvm.AppConstant
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
object RetrofitHelper {

    private var mOkHttpClient: OkHttpClient? = null
    private const val DEFAULT_TIMEOUT = 10
    private const val DEFAULT_READ_TIMEOUT = 20
    private const val DEFAULT_WRITE_TIMEOUT = 20
    private const val NONET_CACHE_TIME = 7 * 24 * 60 // 离线缓存保存一周,单位:秒
    private const val NET_CACHE_TIME = 0 // 有网络时的缓存超时时间,单位:秒

    init {
        initOkHttp()
    }

    private fun initOkHttp() {
        //声明缓存地址和大小，目前只支持Get请求缓存
        val cache = Cache(this.getCacheDir(GlobalCache.getContext(), "testmvvm"), 10 * 1024 * 1024)
        mOkHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .cache(cache)
            .addInterceptor(LogInterceptor())
            .addInterceptor(XInterceptor.NoNetCacheInterceptor(NONET_CACHE_TIME, GlobalCache.getContext()))
            .addNetworkInterceptor(XInterceptor.NetCacheInterceptor(NET_CACHE_TIME))
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(DEFAULT_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()
    }

    fun <T> getApiService(baseUrl: String, clz: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(mOkHttpClient!!)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(clz)
    }

    /**
     * 根据传入的uniqueName获取硬盘缓存的路径地址。
     */
    fun getCacheDir(context: Context, uniqueName: String): File {
        val cachePath: String
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.externalCacheDir!!.getPath()
        } else {
            cachePath = context.getCacheDir().getPath()
        }
        return File(cachePath + File.separator + uniqueName)
    }
}