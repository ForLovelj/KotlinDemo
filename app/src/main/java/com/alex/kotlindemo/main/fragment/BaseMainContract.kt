package com.alex.kotlindemo.main.fragment

import com.alex.kotlindemo.base.IBasePresenter
import com.alex.kotlindemo.base.IBaseView

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */
interface BaseMainContract {


    interface View : IBaseView{

    }

   interface Presenter : IBasePresenter<View>{

   }
}