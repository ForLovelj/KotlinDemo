package com.alex.kotlindemo.Adapter

import android.support.annotation.LayoutRes
import android.widget.ImageView
import com.alex.kotlindemo.R
import com.alex.kotlindemo.model.DouBanMeizi
import com.alex.kotlindemo.utils.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by dth
 * Des:
 * Date: 2018/12/13.
 */
class DouBanAdapter(@LayoutRes layoutResId: Int) : BaseQuickAdapter<DouBanMeizi, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: DouBanMeizi) {
        val imageView = helper.getView<ImageView>(R.id.iv_meizhi)
        GlideApp.with(helper.itemView.context).load(item.url).transition(DrawableTransitionOptions().crossFade(300)).into(imageView)
        helper.setText(R.id.tv_title, item.title)
    }
}