package com.alex.kotlindemo.http

import com.alex.kotlindemo.App
import com.alex.kotlindemo.http.cache.CacheProviders
import com.alex.kotlindemo.http.network.IApi
import com.alex.kotlindemo.http.network.Net
import com.alex.kotlindemo.model.DouBanMeizi
import com.alex.kotlindemo.parser.ParseDouBanMeiZi
import com.alex.kotlindemo.utils.AppCacheUtils
import com.alex.kotlindemo.utils.VLog
import io.reactivex.Observable
import io.rx_cache2.DynamicKeyGroup
import io.rx_cache2.EvictDynamicKeyGroup
import io.rx_cache2.Source
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker

/**
 * Created by dth
 * Des: 统一的数据管理类
 * Date: 2018-01-23.
 */

class AppDataManager private constructor() : IDataManager {


    private val mIApi: IApi
    private val mCacheProviders: CacheProviders

    init {
        mIApi = Net.instence.create()
        mCacheProviders = RxCache.Builder()
                .persistence(AppCacheUtils.getRxCacheDir(App.appContext), GsonSpeaker())
                .using(CacheProviders::class.java)
    }

    companion object {


        private var mAppDataManager: AppDataManager? = null

        val instence: AppDataManager
            get() {
                if (mAppDataManager == null) {
                    synchronized(this) {
                        if (mAppDataManager == null) {
                            mAppDataManager = AppDataManager()
                        }
                    }
                }
                return mAppDataManager!!
            }
    }

    override fun getApi(): IApi = mIApi

    override fun listDouBanMeiZhi(cid: Int, page: Int, pullToRefresh: Boolean): Observable<List<DouBanMeizi>> {
        val dynamicKeyGroup = DynamicKeyGroup(cid, page)
        val evictDynamicKeyGroup = EvictDynamicKeyGroup(pullToRefresh)
        val stringObservable = mIApi.listDouBanMeiZhi(cid, page).map {body -> body.string()}
        return mCacheProviders.douBanMeiZi(stringObservable, dynamicKeyGroup, evictDynamicKeyGroup)
                .map{replyString ->
                    when (replyString.source) {
                        Source.CLOUD -> VLog.w("数据来自：网络")
                        Source.MEMORY -> VLog.w("数据来自：内存")
                        Source.PERSISTENCE -> VLog.w("数据来自：磁盘缓存")
                    }
                    replyString.data
                }
                .map{s -> ParseDouBanMeiZi.JsoupDoubanMeizi(s, cid)}
    }
}
