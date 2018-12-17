package com.alex.kotlindemo.main

import android.os.Bundle
import android.support.annotation.IntRange
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.alex.kotlindemo.R
import com.alex.kotlindemo.base.BaseActivity
import com.alex.kotlindemo.constants.PermissionConstants
import com.alex.kotlindemo.ui.picture.MainDouBanFragment
import com.alex.kotlindemo.ui.video.VideoFragment
import com.alex.kotlindemo.utils.FragmentUtils
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.sdsmdg.tastytoast.TastyToast
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by dth
 * Des:
 * Date: 2018/12/5.
 */
class MainActivity : BaseActivity<MainContract.Presenter, MainContract.View>(), MainContract.View {

    private var fragmentManager: FragmentManager? = null
    private var selectIndex: Int = 0
    private var mCurrentFragment: Fragment? = null
    private var mVideoFragment: Fragment? = null
    private var mMainDouBanFragment: Fragment? = null
    private val permission = PermissionConstants.getPermissions(PermissionConstants.STORAGE)


    override fun init(savedInstanceState: Bundle?) {

        makeCheckPermission()
        fragmentManager = supportFragmentManager
        initBottomNavigationBar(selectIndex)

    }

    override fun getBundleExtras(bundle: Bundle) {

    }

    override fun tellMeLayout(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter(): MainPresenter = MainPresenter()


    private fun initBottomNavigationBar(@IntRange(from = 0, to = 4) selectIndex: Int) {
        bottomNavigationBar.addItem(BottomNavigationItem(ContextCompat.getDrawable(this,R.drawable.ic_video_library_black_24dp),R.string.title_video))
        bottomNavigationBar.addItem(BottomNavigationItem(ContextCompat.getDrawable(this,R.drawable.ic_photo_library_black_24dp),R.string.title_photo))
        bottomNavigationBar.addItem(BottomNavigationItem(ContextCompat.getDrawable(this,R.drawable.ic_library_books_black_24dp),R.string.title_forum))
        bottomNavigationBar.addItem(BottomNavigationItem(ContextCompat.getDrawable(this,R.drawable.ic_library_music_black_24dp),R.string.title_music))
        bottomNavigationBar.addItem(BottomNavigationItem(ContextCompat.getDrawable(this,R.drawable.ic_menu_black_24dp),R.string.title_me))

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
        bottomNavigationBar.activeColor = R.color.bottom_navigation_bar_active
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)

        bottomNavigationBar.setFirstSelectedPosition(selectIndex)

        bottomNavigationBar.setTabSelectedListener(object : BottomNavigationBar.SimpleOnTabSelectedListener() {
            override fun onTabSelected(position: Int) {
                doOnTabSelected(position)
            }
        })

        bottomNavigationBar.setBarBackgroundColor(R.color.bottom_navigation_bar_background)
        bottomNavigationBar.setFab(fabSearch)
        bottomNavigationBar.initialise()

    }

    private fun doOnTabSelected(position: Int) {

        when (position) {
            0 -> {
                if (mVideoFragment == null) {
                    mVideoFragment = VideoFragment()
                }
                mCurrentFragment = FragmentUtils.switchContent(fragmentManager, mCurrentFragment, mVideoFragment, R.id.content, position.toLong(), false)
                showFloatingActionButton(fabSearch)
            }
            1 -> {
                if (mMainDouBanFragment == null) {
                    mMainDouBanFragment = MainDouBanFragment()
                }
                mCurrentFragment = FragmentUtils.switchContent(fragmentManager, mCurrentFragment, mMainDouBanFragment, R.id.content, position.toLong(), false)
                showFloatingActionButton(fabSearch)
            }
            else -> {
            }


        }
    }

    private fun showFloatingActionButton(fabSearch: FloatingActionButton) {
        fabSearch.show(object : FloatingActionButton.OnVisibilityChangedListener() {
            override fun onShown(fab: FloatingActionButton?) {
                fabSearch.requestLayout()
                bottomNavigationBar.setFab(fab!!)
            }
        })
    }

    private fun hideFloatingActionButton(fabSearch: FloatingActionButton) {
        val layoutParams = fabSearch.layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            val behavior = FloatingActionButton.Behavior()
            layoutParams.behavior = behavior
        }
        fabSearch.hide()
    }

    private fun makeCheckPermission() {
        if (!AndPermission.hasPermissions(this, Permission.Group.STORAGE)) {
            AndPermission.with(this)
                    .runtime()
                    .permission(Permission.Group.STORAGE)
                    .rationale{context, data, executor ->
                        executor.execute()
                    }
                    .onGranted{
                        showMessage("读取权限申请成功",TastyToast.SUCCESS)
                    }
                    .onDenied{ permissions ->
                        val permissionNames = Permission.transformText(mActivity, permissions)
                        val permissionText = TextUtils.join(",\n", permissionNames)
                        showMessage(permissionText,TastyToast.ERROR)
                    }
                    .start()
        }
    }

}
