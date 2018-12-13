package com.alex.kotlindemo.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.sdsmdg.tastytoast.TastyToast
import io.reactivex.disposables.Disposable

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

abstract class BaseFragment<T : IBasePresenter<V>, V : IBaseView> : Fragment(), IBaseView {

    protected var mContext: Context? = null
    protected var mPresenter: T? = null
    protected var isViewInitiated: Boolean = false
    protected var isVisibleToUser: Boolean = false
    protected var isDataInitiated: Boolean = false
    protected var mBind: Unbinder? = null

    protected val presenter: T
        get() {
            if (mPresenter == null) {
                throw RuntimeException("presenter cannot be initialized!")
            }
            return mPresenter as T
        }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(tellMeLayout(), container, false)
        mBind = ButterKnife.bind(this, view)

        mPresenter = initPresenter()
        if (mPresenter != null) {
            mPresenter?.attachView(this as V)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareFetchData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }

    protected abstract fun fetchData()

    protected fun prepareFetchData(): Boolean {
        return prepareFetchData(false)
    }

    /**
     * 预留强制刷新数据
     * @param forceUpdate true 强制刷新
     * @return
     */
    private fun prepareFetchData(forceUpdate: Boolean): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData()
            isDataInitiated = true
            return true
        }
        return false
    }

    /**
     * 初始化方法
     */
    protected abstract fun init(view: View, savedInstanceState: Bundle?)

    /**
     * 布局
     * @return
     */
    protected abstract fun tellMeLayout(): Int

    protected abstract fun initPresenter(): T

    protected fun showMessage(msg: String, type: Int){
        TastyToast.makeText(mContext?.applicationContext, msg, TastyToast.LENGTH_SHORT, type).show()
    }

    override fun showLoadingView(showText: String) {}


    override fun dissmissLoadingView() {}

    override fun showErrorView() {
        dissmissLoadingView()
    }

    override fun showEmptyView() {
        dissmissLoadingView()
    }

    override fun showDataView() {
        dissmissLoadingView()
    }

    override fun showException(msg: String) {}

    override fun addDisposable(disposable: Disposable) {
        mPresenter?.addDisposable(disposable)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        isDataInitiated = false
        isViewInitiated = false
        super.onDestroyView()
    }

    override fun onDestroy() {

        if (mPresenter != null) {
            mPresenter!!.detachView()
            mPresenter = null
        }
        mBind?.unbind()
        mContext = null
        super.onDestroy()
    }
}
