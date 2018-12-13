package com.alex.kotlindemo.ui.picture

import com.alex.kotlindemo.base.IBasePresenter
import com.alex.kotlindemo.base.IBaseView
import com.alex.kotlindemo.model.DouBanMeizi

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */
interface PictureContract {

    interface View : IBaseView {
        fun setData(data: List<DouBanMeizi>)
        fun setMoreData(data: List<DouBanMeizi>)
        fun noMoreData()

    }

    interface Presenter : IBasePresenter<View> {

        fun listDouBanMeiZhi(categoryId: Int, pullToRefresh: Boolean)
    }
}