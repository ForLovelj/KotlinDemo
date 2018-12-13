package com.alex.kotlindemo.base

import io.reactivex.disposables.Disposable

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */
interface IBasePresenter<V: IBaseView> {

    fun attachView(view: V)

    fun detachView()

    fun addDisposable(d: Disposable)

    fun unDisposable()
}
