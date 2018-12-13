package com.alex.kotlindemo.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */
class MyFragmentPagerAdapter : FragmentPagerAdapter {

    private var mFragment: MutableList<Fragment>? = null
    private var mTitleList: List<String>? = null

    constructor(fm: FragmentManager, mFragment: MutableList<Fragment>) : super(fm) {
        this.mFragment = mFragment
    }

    /**
     * 接收传递的标题
     */
    constructor(fm: FragmentManager, fragment: MutableList<Fragment>, titleList: List<String>) : super(fm) {
        this.mFragment = fragment
        this.mTitleList = titleList
    }

    override fun getItem(position: Int): Fragment? {
        return mFragment?.get(position)
    }

    override fun getCount(): Int {
        return mFragment?.size ?: 0
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return mTitleList?.get(position) ?: ""
    }

    fun addFragmentList(fragment: MutableList<Fragment>) {
        this.mFragment?.clear()
        this.mFragment = null
        this.mFragment = fragment
        notifyDataSetChanged()
    }
}
