package com.alex.kotlindemo.ui.video

import com.alex.kotlindemo.base.IBasePresenter
import com.alex.kotlindemo.base.IBaseView

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */
interface VideoContract {

    interface View : IBaseView {

    }

    interface Presenter : IBasePresenter<View> {

    }
}