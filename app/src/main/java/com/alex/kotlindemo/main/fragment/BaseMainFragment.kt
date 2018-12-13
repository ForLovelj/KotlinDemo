package com.alex.kotlindemo.main.fragment

import android.os.Bundle
import android.view.View
import com.alex.kotlindemo.Adapter.MyFragmentPagerAdapter
import com.alex.kotlindemo.R
import com.alex.kotlindemo.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */
abstract class BaseMainFragment : BaseFragment<BaseMainContract.Presenter,BaseMainContract.View>(),BaseMainContract.View {

    private  var myFragmentPagerAdapter: MyFragmentPagerAdapter? = null

    override fun fetchData() {
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        myFragmentPagerAdapter = initFragmentAdapter()
        viewPager.adapter = myFragmentPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun tellMeLayout(): Int {
        return R.layout.fragment_main
    }

    override fun initPresenter(): BaseMainContract.Presenter {
        return BaseMainPresenter()
    }

    protected abstract fun initFragmentAdapter(): MyFragmentPagerAdapter
}