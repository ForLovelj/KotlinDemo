package com.alex.kotlindemo.http.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by dth
 * Des: 自定义拦截器
 * Date: 2018-01-23.
 */

class CustomInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        //添加请求参数
        val url = original.url().newBuilder()
                //                .addQueryParameter("count", "5")
                //                .addQueryParameter("start", "0")
                .build()

        //添加请求头
        val request = original.newBuilder()
                //                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                //                .addHeader("Connection", "keep-alive")
                //                .addHeader("token", mIPreference.getToken())
                //                .addHeader("token","7ee9163da415eb7a5a3f6c7743914fbc")
                //                .removeHeader("User-Agent")
                //                .addHeader("User-Agent", CommonUtils.getUserAgent())
                .method(original.method(), original.body())
                .url(url)
                .build()


        return chain.proceed(request)
    }
}
