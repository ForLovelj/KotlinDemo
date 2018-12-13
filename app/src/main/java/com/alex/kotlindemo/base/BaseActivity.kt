package com.alex.kotlindemo.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.sdsmdg.tastytoast.TastyToast
import io.reactivex.disposables.Disposable

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

abstract class BaseActivity<T : IBasePresenter<V>,V : IBaseView> : AppCompatActivity(), IBaseView {

    protected var mActivity: Activity? = null
    protected var mPresenter: T? = null
    protected var mBind: Unbinder? = null

    protected val presenter: T
        get() {
            return mPresenter ?: throw RuntimeException("presenter cannot be initialized!")
//            if (mPresenter == null) {
//                throw RuntimeException("presenter cannot be initialized!")
//            }
//            return mPresenter as T
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivity = this
        setContentView(tellMeLayout())
        mBind = ButterKnife.bind(this)

        mPresenter = initPresenter()
        if (mPresenter != null) {
            mPresenter?.attachView(this as V)
        }


        //bundle
        val bundle = intent.extras
        if (null != bundle) {
            getBundleExtras(bundle)
        }


        init(savedInstanceState)

    }

    /**
     * 初始化方法
     */
    protected abstract fun init(savedInstanceState: Bundle?)

    /**
     * 传递bundle数据
     * @param bundle
     */
    protected abstract fun getBundleExtras(bundle: Bundle)

    /**
     * 布局
     * @return
     */
    protected abstract fun tellMeLayout(): Int

    protected abstract fun initPresenter(): T

    protected fun showMessage(msg: String, type: Int){
        TastyToast.makeText(mActivity?.applicationContext, msg, TastyToast.LENGTH_SHORT, type).show()
    }

    fun startActivityWithAnim(intent: Intent) {
        startActivity(intent)
    }

    override fun finish() {
        super.finish()
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

    public override fun onStop() {
        super.onStop()
    }

    public override fun onDestroy() {

        if (mPresenter != null) {
            mPresenter?.detachView()
            mPresenter = null
        }
        mBind?.unbind()
        mActivity = null
        super.onDestroy()
    }

}
