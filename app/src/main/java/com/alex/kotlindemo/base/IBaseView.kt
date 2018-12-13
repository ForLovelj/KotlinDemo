package com.alex.kotlindemo.base


import io.reactivex.disposables.Disposable

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

interface IBaseView : BaseMvpView {

    /**
     * 数据页面
     */
    fun showDataView()

    /**
     * 空数据页面
     */
    fun showEmptyView()

    /**
     * 错误页面
     */
    fun showErrorView()

    /**
     * loading页面
     * @param showText
     */
    fun showLoadingView(showText: String)

    /**
     * 取消loading
     */
    fun dissmissLoadingView()

    /**
     * 处理异常错误
     * @param msg
     */
    fun showException(msg: String)

    /**
     *
     * @param disposable
     */
    fun addDisposable(disposable: Disposable)
}
