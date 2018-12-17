package com.alex.kotlindemo.http.network

import android.util.Log
import com.alex.kotlindemo.App
import com.alex.kotlindemo.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit



/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

class Net private constructor() {

    val douban: IApi
        get() = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(provideOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IApi::class.java)

    init {

        val okHttpClient = provideOkHttpClient()

        mRetrofit = provideRetrofit(okHttpClient)

    }

    fun create(): IApi {

        return mRetrofit?.create(IApi::class.java)!!
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())//直接接受返回值为String
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        // TODO: 17-8-30
        // 1. 基本授权
        // 2. 基础Url设置
        // 3. 重试方案 For https://github.com/airbnb/okreplay library, recording and replaying server responses.
        // 4. ...
        // 5. 添加 log 打印

        //添加缓存
        val cacheFile = File(App.appContext!!.externalCacheDir, "cache")
        //缓存大小为10M
        val cacheSize = 20 * 1024 * 1024
        //创建缓存对象
        val cache = Cache(cacheFile, cacheSize.toLong())


        val builder = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
//                .cache(cache)
                .addInterceptor(CustomInterceptor())
//                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(HttpLoggingInterceptor()
                        .setLevel(if (BuildConfig.DEBUG || Log.isLoggable("OkHttp", Log.VERBOSE))
                            HttpLoggingInterceptor.Level.BODY
                        else
                            HttpLoggingInterceptor.Level.NONE))
        return builder.build()
    }

    companion object {

        private var mRetrofit: Retrofit? = null
        private val API_BASE_URL = "http://www.dbmeinv.com/"
        private const val CONNECT_TIME_OUT = 15
        private const val WRITE_TIME_OUT = 15
        private const val READ_TIME_OUT = 15

        val instance = NetHolder.holder

        private object NetHolder {
            val holder= Net()
        }

        private val REWRITE_CACHE_CONTROL_INTERCEPTOR = Interceptor { chain ->
            val request = chain.request()

            val originalResponse = chain.proceed(request)
            if (NetworkUtil.isNetAvailable(App.appContext)) {
                val maxAge = 60 // 在线缓存在1分钟内可读取
                originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28 // 离线时缓存保存4周
                originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
            }
        }
    }


}
