package com.alex.kotlindemo.ui.picture.preview

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.alex.kotlindemo.R
import com.alex.kotlindemo.base.BaseActivity
import com.alex.kotlindemo.utils.AppCacheUtils
import com.alex.kotlindemo.utils.FileUtils
import com.alex.kotlindemo.utils.GlideApp
import com.alex.kotlindemo.utils.StatusBarUtil
import com.alex.kotlindemo.view.dialog.CommonHolder
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.florent37.viewanimator.ViewAnimator
import com.orhanobut.dialogplus.DialogPlus
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_image_pre.*
import java.io.File
import java.io.IOException
import java.util.*

/**
 * Created by dth
 * Des:
 * Date: 2018/12/14.
 */

class ImagePreActivity : BaseActivity<ImagePreContract.Presenter,ImagePreContract.View>(),ImagePreContract.View {

    private var isFullScreen = false
    private var imagUrl: String? = null
    private var title: String? = null

    override fun init(savedInstanceState: Bundle?) {
        goFullScreen()
        fixSwipeBack()
        //透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //透明导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        photoView.setOnLongClickListener {
            showSavePictureDialog(imagUrl)
            true
        }
        photoView.setOnClickListener { if (isFullScreen) exitFullScreen() else  goFullScreen() }
    }

    override fun getBundleExtras(bundle: Bundle) {

        imagUrl = bundle.getString("imgUrl")
        title = bundle.getString("title")
        GlideApp.with(this).load(imagUrl).into(photoView)
        ViewCompat.setTransitionName(photoView, TRANSITION_NAME)
        toolBar.title = title
    }

    override fun tellMeLayout(): Int {
        return R.layout.activity_image_pre
    }

    override fun initPresenter(): ImagePreContract.Presenter {
        return ImagePrePresenter()
    }

    companion object {
        private const val TRANSITION_NAME: String = "img"
        fun startActivity(activity: Activity,transitionView: View,url: String,title: String){
            val intent = Intent(activity, ImagePreActivity::class.java)
            intent.putExtra("imgUrl", url)
            intent.putExtra("title", title)
            // 这里指定了共享的视图元素
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, TRANSITION_NAME)
            ActivityCompat.startActivity(activity, intent, options.toBundle())
        }
    }

    protected fun goFullScreen() {
        isFullScreen = true
        ViewAnimator.animate(toolBar)
                .alpha(1f,0f)
                .duration(500)
                .onStart { setUiFlags(true) }
                .start()
    }

    protected fun exitFullScreen() {
        isFullScreen = false
        ViewAnimator.animate(toolBar)
                .alpha(0f,1f)
                .duration(500)
                .onStart { setUiFlags(false) }
                .start()
    }

    private fun setUiFlags(fullscreen: Boolean) {
        val decorView = window.decorView
            decorView.systemUiVisibility = if (fullscreen) getFullscreenUiFlags() else View.SYSTEM_UI_FLAG_VISIBLE
    }

    private fun getFullscreenUiFlags(): Int {
        var flags = View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            flags = flags or (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            flags = flags or View.SYSTEM_UI_FLAG_IMMERSIVE
        }
        return flags
    }

    private fun fixSwipeBack() {
        val viewGroup = window.decorView.findViewById<ViewGroup>(android.R.id.content)
        //就是这里了,有些statusbar库为了模拟状态栏，可能设置了padding,会在视频上方出现一条横幅，看上去好像状态栏没隐藏，其实已经隐藏了，这个是假的，错觉，所以重新设置padding为0即可
        viewGroup.setPadding(0, 0, 0, 0)
        toolBar.setPadding(0,StatusBarUtil.getStatusBarHeight(this),0,0)

    }

    private fun showSavePictureDialog(imgUrl: String?) {
        DialogPlus.newDialog(this)
                .setContentHolder(CommonHolder(View.inflate(this,R.layout.dialog_common,null))
                        .setContent("保存图片？"))
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setOnClickListener { dialog, view ->
                    when (view.id) {
                        R.id.tv_ok -> {
                            GlideApp.with(this)
                                    .downloadOnly()
                                    .load(imgUrl)
                                    .into(object : SimpleTarget<File>(){
                                        override fun onResourceReady(resource: File, transition: Transition<in File>?) {

                                            val filePath = File(AppCacheUtils.DOWNLOAD_IMAGE_PATH)
                                            if (!filePath.exists()) {
                                                if (!filePath.mkdirs()) {
                                                    showMessage("创建文件夹失败", TastyToast.ERROR)
                                                    return
                                                }
                                            }

                                            val file = File(filePath, UUID.randomUUID().toString() + ".jpg")
                                            try {
                                                FileUtils.copyFile(resource, file)
                                                MediaScannerConnection.scanFile(mActivity, arrayOf(file.absolutePath), arrayOf("image/jpeg"), null)
                                                showMessage("保存图片成功", TastyToast.SUCCESS)
                                            } catch (e: IOException) {
                                                e.printStackTrace()
                                                showMessage("保存图片失败", TastyToast.ERROR)
                                            }

                                            dialog.dismiss()

                                        }

                                        override fun onLoadFailed(errorDrawable: Drawable?) {
                                            showMessage("图片下载失败",TastyToast.ERROR)
                                            dialog.dismiss()
                                        }

                                    })

                        }
                        R.id.tv_cancel -> dialog.dismiss()
                    }
                }
                .create()
                .show()
    }
}