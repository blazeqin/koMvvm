package com.lion.komvvm.ui.home

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.lion.komvvm.R
import com.lion.komvvm.entity.BannerBean
import com.lion.komvvm.utils.LineItemDecoration
import com.lion.mvvmlib.base.BaseFragment
import com.stx.xhb.androidx.XBanner
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel, ViewDataBinding>() {

    private val mAdapter by lazy { HomeListAdapter() }
    private lateinit var mBanner: XBanner
    private var page = 0

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
//            setOnLoadMoreListener()
            setOnItemClickListener { adapter, _, position ->
                println("click item")
            }
        }
        //pull to refresh
        srl_home.setOnRefreshListener { }
    }

    //load data on lazy
    override fun lazyLoadData() {
        mViewModel.apply {
            //get data
            getBanner()
            getHomeList(page)
            //live data: add observer
            mBannerData.observe(this@HomeFragment, Observer {
                mBanner.setBannerData(it)
            })
            mDatas.observe(this@HomeFragment, Observer {
                if (srl_home.isRefreshing) srl_home.isRefreshing = false
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
                page = it.curPage
            })
        }
    }
}