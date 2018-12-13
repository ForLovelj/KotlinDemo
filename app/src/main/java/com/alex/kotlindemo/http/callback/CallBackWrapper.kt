package com.alex.kotlindemo.http.callback

import android.text.TextUtils
import android.widget.Toast
import com.alex.kotlindemo.App
import com.alex.kotlindemo.base.BaseResponse
import com.alex.kotlindemo.base.IBaseView
import com.alex.kotlindemo.utils.VLog
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

abstract class CallBackWrapper<T : BaseResponse<*>>(private val mIBaseView: IBaseView) : Observer<T> {


    override fun onSubscribe(@NonNull d: Disposable) {
        mIBaseView.addDisposable(d)
    }

    override fun onNext(@NonNull response: T) {

        mIBaseView.showDataView()
        VLog.d("onNext: %s", response)
        when (response.code) {
            BaseResponse.RESULT_CODE_SUCCESS -> onSuccess(response)
            BaseResponse.RESULT_CODE_TOKEN_EXPIRED -> {

            }
            else -> onDataFailure(response)
        }
    }

    override fun onError(@NonNull e: Throwable) {

        //异常处理，需要自己实现
        VLog.e("onError: " + e.toString())
        mIBaseView.dissmissLoadingView()
        handleError(e, mIBaseView)
    }

    override fun onComplete() {

        mIBaseView.dissmissLoadingView()
    }

    abstract fun onSuccess(response: T)

    /**
     * 对api返回的错误状态的处理 需要时自己实现
     * @param response
     */
    protected fun onDataFailure(response: T) {
        val msg = response.msg
        VLog.w("request data but get failure:" + msg!!)
        if (!TextUtils.isEmpty(msg)) {
            //            mBaseMvpView.showException(response.getMsg());
            Toast.makeText(App.appContext, msg, Toast.LENGTH_SHORT).show()
        } else {
            //            mBaseMvpView.showException("未知错误");
            Toast.makeText(App.appContext, "未知错误", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 按照通用规则解析和处理数据请求时发生的错误。这个方法在执行支付等非标准的REST请求时很有用。
     */
    fun handleError(throwable: Throwable?, iBaseView: IBaseView) {
        if (throwable == null) {
            Toast.makeText(App.appContext, "未知错误", Toast.LENGTH_SHORT).show()
            return
        }
        //分为以下几类问题：网络连接，数据解析，客户端出错【空指针等】，服务器内部错误
        if (throwable is SocketTimeoutException
                || throwable is ConnectException
                || throwable is UnknownHostException
                || throwable is IOException) {
            Toast.makeText(App.appContext, "网络异常", Toast.LENGTH_SHORT).show()
            iBaseView.showErrorView()
        } else if (throwable is JsonSyntaxException || throwable is NumberFormatException || throwable is MalformedJsonException) {
            Toast.makeText(App.appContext, "数据解析异常", Toast.LENGTH_SHORT).show()
        } else if (throwable is HttpException) {
            Toast.makeText(App.appContext, "服务器错误" + throwable.code(), Toast.LENGTH_SHORT).show()
        } else if (throwable is NullPointerException) {
            Toast.makeText(App.appContext, "客户端异常" + throwable.message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(App.appContext, "未知错误", Toast.LENGTH_SHORT).show()
        }
    }
}
