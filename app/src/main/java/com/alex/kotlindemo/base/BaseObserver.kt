package com.alex.kotlindemo.base

import com.alex.kotlindemo.utils.VLog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by dth
 * Des:
 * Date: 2018/12/13.
 */
abstract class BaseObserver<T>(private val iBaseView: IBaseView) : Observer<T> {

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {
        iBaseView.addDisposable(d)
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        VLog.e(e)
        onFailed(e)
    }

    protected abstract fun onSuccess(t: T)

    protected abstract fun onFailed(e: Throwable)

}