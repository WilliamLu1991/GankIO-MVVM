package com.williamlu.datalib.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: WilliamLu
 * @Date: 2019/10/22
 * @Description: https://www.jianshu.com/p/ea2055db3dd3
 */
public class XInterceptor {
    /**
     * 自定义的，重试N次的拦截器
     * 通过：addInterceptor 设置
     */
    public static class RetryInterceptor implements Interceptor {

        public int maxRetry;//最大重试次数
        private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

        public RetryInterceptor(int maxRetry) {
            this.maxRetry = maxRetry;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            Log.i("RetryInterceptor","num:"+retryNum);
            while (!response.isSuccessful() && retryNum < maxRetry) {
                retryNum++;
                Log.i("RetryInterceptor","num:"+retryNum);
                response = chain.proceed(request);
            }
            return response;
        }
    }

    /**
     * 设置没有网络的情况下，
     *  的缓存时间
     *  通过：addInterceptor 设置
     */
    public static class NoNetCacheInterceptor implements Interceptor {

        private int     maxCacheTime = 0;
        private Context applicationContext;

        public NoNetCacheInterceptor(int maxCacheTime, Context applicationContext) {
            this.maxCacheTime = maxCacheTime;
            this.applicationContext = applicationContext;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            if (!isConnected(applicationContext)) {
                CacheControl tempCacheControl = new CacheControl.Builder()
                        .onlyIfCached()
                        .maxStale(maxCacheTime, TimeUnit.SECONDS)
                        .build();
                request = request.newBuilder()
                        .cacheControl(tempCacheControl)
                        .build();
            }
            return chain.proceed(request);
        }
    }

    /**
     * 设置在有网络的情况下的缓存时间
     *  在有网络的时候，会优先获取缓存
     * 通过：addNetworkInterceptor 设置
     */
    public static class NetCacheInterceptor implements Interceptor {

        private int maxCacheTime = 0;

        public NetCacheInterceptor(int maxCacheTime) {
            this.maxCacheTime = maxCacheTime;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response originalResponse = chain.proceed(request);
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxCacheTime)
                    .build();
        }
    }

    /**
     * 判断网络是否连接
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @param context 上下文
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isConnected();
    }
    /**
     * 获取活动网络信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}
