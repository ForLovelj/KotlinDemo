package com.alex.kotlindemo

import android.app.Application
import android.content.Context

/**
 * Created by dth
 * Des:
 * Date: 2018/12/5.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    /**
     * 伴生类
     */
    companion object {
        var appContext: Context? = null

    }

}
