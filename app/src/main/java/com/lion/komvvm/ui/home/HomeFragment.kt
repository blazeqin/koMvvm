package com.lion.komvvm.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.lion.komvvm.R
import com.lion.komvvm.entity.ArticlesBean
import com.lion.komvvm.entity.BannerBean
import com.lion.komvvm.entity.HomeListBean
import com.lion.komvvm.ui.activity.WebviewActivity
import com.lion.komvvm.utils.LineItemDecoration
import com.lion.mvvmlib.base.BaseFragment
import com.lion.mvvmlib.util.EXTRA_URL
import com.stx.xhb.androidx.XBanner
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel, ViewDataBinding>() {

    private val mAdapter by lazy { HomeListAdapter() }
    private lateinit var mBanner: XBanner

    override fun layoutId() = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        with(rv_home) {
            //set recyclerView
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addItemDecoration(LineItemDecoration(context))

            //set banner
            mBanner = XBanner(context)
            mBanner.minimumWidth = MATCH_PARENT
            mBanner.layoutParams =
                ViewGroup.LayoutParams(MATCH_PARENT, resources.getDimension(R.dimen.dp_140).toInt())
            mBanner.loadImage { banner, model, view, position ->
                Glide.with(view).load((model as BannerBean).xBannerUrl).into(view as ImageView)
            }
        }

        //set adapter
        with(mAdapter) {
            addHeaderView(mBanner)
            setOnLoadMoreListener(this@HomeFragment::loadMore, rv_home)
            setOnItemClickListener { adapter, _, position ->
                val item = adapter.data[position] as ArticlesBean
                val intent = Intent(context, WebviewActivity::class.java)
                intent.putExtra(EXTRA_URL, item.link)
                startActivity(intent)
            }
        }
        //pull to refresh
        srl_home.setOnRefreshListener {
            srl_home.isRefreshing = true
            mViewModel.getHomeList()
        }
    }

    //load data on lazy: useless???
    override fun lazyLoadData() {
        mViewModel.apply {
            //get data
            getBanner()
            getHomeList()
            //live data: add observer
            mBannerData.observe(this@HomeFragment, Observer {
                mBanner.setBannerData(it)
            })
            mDatas.observe(this@HomeFragment, Observer {
                if (srl_home.isRefreshing) srl_home.isRefreshing = false
                LogUtils.i("current page: ${it.curPage}")
                //handle data
                if (it.curPage == 1) {
                    mAdapter.setNewData(it.datas)
                }else
                    mAdapter.addData(it.datas)
                //handle adapter load
                if (it.curPage == it.pageCount)
                    mAdapter.loadMoreEnd()
                else
                    mAdapter.loadMoreComplete()
            })
        }
    }

    private fun loadMore() {
        mViewModel.getHomeList(true)
    }
}