package com.alex.kotlindemo.ui.video

import android.os.Bundle
import android.view.View
import com.alex.kotlindemo.R
import com.alex.kotlindemo.base.BaseFragment

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */
class VideoFragment : BaseFragment<VideoContract.Presenter, VideoContract.View>(),VideoContract.View{

    override fun fetchData() {
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
    }

    override fun tellMeLayout(): Int {
       return R.layout.fragment_video
    }

    override fun initPresenter(): VideoPresenter {
       return VideoPresenter()
    }
}