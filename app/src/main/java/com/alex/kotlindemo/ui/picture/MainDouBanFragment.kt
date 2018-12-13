package com.alex.kotlindemo.ui.picture

import android.support.v4.app.Fragment
import com.alex.kotlindemo.Adapter.MyFragmentPagerAdapter
import com.alex.kotlindemo.main.fragment.BaseMainFragment
import com.alex.kotlindemo.utils.VLog

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */
class MainDouBanFragment : BaseMainFragment() {

    val DOUBAN_MEIZI_VALUE = listOf(0, 2, 6, 7, 3, 4, 5)
    val DOUBAN_MEIZI_NAME = listOf("所有", "大胸妹", "小翘臀", "黑丝袜", "美腿控", "有颜值", "大杂烩")

    override fun initFragmentAdapter(): MyFragmentPagerAdapter {

        val fragmentList = ArrayList<Fragment>()
        for (value in DOUBAN_MEIZI_VALUE) {
            fragmentList.add(PictureFragment.instance(value))
        }

        VLog.d("fragmentList: %s",fragmentList.size)
        return MyFragmentPagerAdapter(childFragmentManager,fragmentList,DOUBAN_MEIZI_NAME)
    }
}