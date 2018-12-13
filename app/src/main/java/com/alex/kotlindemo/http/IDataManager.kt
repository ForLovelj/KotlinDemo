package com.alex.kotlindemo.http


import com.alex.kotlindemo.http.network.IApi
import com.alex.kotlindemo.model.DouBanMeizi
import io.reactivex.Observable

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

interface IDataManager {

    fun getApi(): IApi

    fun listDouBanMeiZhi(cid: Int, page: Int, pullToRefresh: Boolean): Observable<List<DouBanMeizi>>
}
