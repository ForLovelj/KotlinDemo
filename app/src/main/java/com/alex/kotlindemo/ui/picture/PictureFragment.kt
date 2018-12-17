package com.alex.kotlindemo.ui.picture

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.alex.kotlindemo.Adapter.DouBanAdapter
import com.alex.kotlindemo.R
import com.alex.kotlindemo.base.BaseFragment
import com.alex.kotlindemo.model.DouBanMeizi
import com.alex.kotlindemo.ui.picture.preview.ImagePreActivity
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.fragment_picture.*

/**
 * Created by dth
 * Des:
 * Date: 2018/12/12.
 */

//const val TYPE: String = "type"
class PictureFragment : BaseFragment<PictureContract.Presenter, PictureContract.View>(),PictureContract.View{

    private var mType: Int? = 0
    private lateinit var mDouBanAdapter: DouBanAdapter

    override fun fetchData() {
        mPresenter?.listDouBanMeiZhi(mType!!,true)
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        mType = arguments?.getInt(TYPE,0)

        swipeLayout.setOnRefreshListener {mPresenter?.listDouBanMeiZhi(mType!!,true)}
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.green), ContextCompat.getColor(context!!, R.color.lightred), ContextCompat.getColor(context!!, R.color.yeloo))
        mDouBanAdapter = DouBanAdapter(R.layout.item_dou_ban_mei_zi)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = mDouBanAdapter

        mDouBanAdapter.setOnLoadMoreListener({
            mPresenter?.listDouBanMeiZhi(mType!!,false)
        },recyclerView)

        mDouBanAdapter.setOnItemClickListener { adapter, view, position ->
            val douBanMeizi = mDouBanAdapter.data[position]
            ImagePreActivity.startActivity(activity!!,view,douBanMeizi.url,douBanMeizi.title)
        }
    }

    override fun tellMeLayout(): Int {
        return R.layout.fragment_picture
    }

    override fun initPresenter(): PictureContract.Presenter {
        return PicturePresenter()
    }



    companion object {
        private const val TYPE: String = "type"

        fun instance(type: Int): PictureFragment {
            val pictureFragment = PictureFragment()
            val bundle = Bundle()
            bundle.putInt(TYPE,type)
            pictureFragment.arguments = bundle
            return pictureFragment
        }
    }

    override fun setData(data: List<DouBanMeizi>) {

        mDouBanAdapter.setNewData(data)
    }

    override fun setMoreData(data: List<DouBanMeizi>) {
        mDouBanAdapter.loadMoreComplete()
        mDouBanAdapter.addData(data)
    }

    override fun noMoreData() {
        mDouBanAdapter.loadMoreEnd(true)
    }


    override fun showLoadingView(showText: String) {
        swipeLayout.isRefreshing = true
    }

    override fun dissmissLoadingView() {
        swipeLayout.isRefreshing = false
    }

    override fun showErrorView() {
        super.showErrorView()
        mDouBanAdapter.loadMoreFail()

        showMessage("网络请求错误", TastyToast.ERROR)
    }

}