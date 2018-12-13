package com.alex.kotlindemo.ui.picture

import com.alex.kotlindemo.base.BaseObserver
import com.alex.kotlindemo.base.BasePresenter
import com.alex.kotlindemo.http.AppDataManager
import com.alex.kotlindemo.model.DouBanMeizi
import com.alex.kotlindemo.utils.RxSchedulersHelper

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */
class PicturePresenter : BasePresenter<PictureContract.View>(),PictureContract.Presenter{

    private var page = 1
    private val pageSize = 20

    override fun listDouBanMeiZhi(categoryId: Int, pullToRefresh: Boolean) {
        if (pullToRefresh) {
            page = 1
        }
        mView?.showLoadingView("")
        AppDataManager.instence.listDouBanMeiZhi(categoryId,page,pullToRefresh)
                .compose(RxSchedulersHelper.ioMainThread())
                .subscribe(object : BaseObserver<List<DouBanMeizi>>(mView!!){

                    override fun onSuccess(t: List<DouBanMeizi>) {
                        mView?.showDataView()
                        if (page == 1) {
                            mView?.setData(t)
                        } else {
                            mView?.setMoreData(t)
                        }

                        if (t.size < pageSize) {
                            mView?.noMoreData()
                            return
                        }
                        page++
                    }

                    override fun onFailed(e: Throwable) {
                        mView?.showErrorView()
                    }

                })
    }

}
