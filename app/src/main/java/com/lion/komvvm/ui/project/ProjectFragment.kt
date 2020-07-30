package com.lion.komvvm.ui.project

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.lion.komvvm.R
import com.lion.mvvmlib.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectFragment : BaseFragment<ProjectViewModel, ViewDataBinding>() {

    private val mPagerAdapter by lazy{ ProjectPageAdapter(this)}

    override fun layoutId() = R.layout.fragment_project

    override fun initView(savedInstanceState: Bundle?) {
        initTabLayout()
    }

    private fun initTabLayout() {
        mViewModel.mTabTitle.observe(this, Observer {list->
            mPagerAdapter.createTabFragment(list)
            vp_project.adapter = mPagerAdapter
            //viewpager2 bind with TabLayout
            TabLayoutMediator(tl_project, vp_project){tab, position ->
                tab.text = list[position].name
            }.attach()
        })
    }

    override fun lazyLoadData() {
        mViewModel.apply {
            getFirstData()
        }
    }
}