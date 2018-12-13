package com.alex.kotlindemo.utils

import android.content.Context

import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.module.AppGlideModule

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */
@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val diskCacheSizeBytes = 1024 * 1024 * 250L
        builder.setDiskCache(DiskLruCacheFactory(AppCacheUtils.getGlideDiskCacheDir(context).absolutePath, diskCacheSizeBytes))
    }
}
