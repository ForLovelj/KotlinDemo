package com.alex.kotlindemo.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

open class BasePresenter<V : IBaseView> : IBasePresenter<V>{

    protected var mView: V? = null
    private  var mCompositeDisposable: CompositeDisposable? = null


    override fun attachView(view: V) {
        this.mView = view
    }

    override fun detachView() {
        if (mView != null) {
            mView = null
        }
    }

    override fun addDisposable(d: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
            mCompositeDisposable?.add(d)
    }

    override fun unDisposable() {

        if (mCompositeDisposable != null) {
            mCompositeDisposable?.dispose()
        }
    }

}
