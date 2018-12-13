package com.alex.kotlindemo.http.cache

import io.reactivex.Observable
import io.rx_cache2.*
import java.util.concurrent.TimeUnit

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */
interface CacheProviders {
    /**
     * 缓存自动过期时间15分钟
     */
    companion object {
        const val CACHE_TIME = 15L
    }

    /**
     * 豆瓣妹纸分类
     *
     * @param observable           ob
     * @param dynamicKeyGroup      页码分类
     * @param evictDynamicKeyGroup 缓存控制
     * @return ob
     */
    @ProviderKey("dou_ban_mei_zi_pic_list")
    @LifeCache(duration = CACHE_TIME, timeUnit = TimeUnit.MINUTES)
    fun douBanMeiZi(observable: Observable<String>, dynamicKeyGroup: DynamicKeyGroup, evictDynamicKeyGroup: EvictDynamicKeyGroup): Observable<Reply<String>>

}