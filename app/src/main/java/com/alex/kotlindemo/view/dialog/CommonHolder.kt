package com.alex.kotlindemo.view.dialog

import android.view.View
import android.widget.TextView
import com.alex.kotlindemo.R
import com.orhanobut.dialogplus.ViewHolder

/**
 * Created by dth
 * Des:
 * Date: 2018/12/14.
 */
class CommonHolder(contentView: View) : ViewHolder(contentView) {

    private val tvContent: TextView = contentView.findViewById(R.id.tv_content)

    fun setContent(content: String?): CommonHolder{
        tvContent.text = content ?: ""
        return this
    }

    override fun getInflatedView(): View {
        return super.getInflatedView()
    }
}
