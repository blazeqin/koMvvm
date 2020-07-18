package com.lion.komvvm.ui.project

import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.lion.komvvm.R
import com.lion.komvvm.databinding.FragmentProjectBinding
import com.lion.komvvm.entity.ArticlesBean
import com.lion.komvvm.ui.activity.WebviewActivity
import com.lion.mvvmlib.base.BaseFragment
import com.lion.mvvmlib.base.EventItem
import com.lion.mvvmlib.util.EXTRA_URL
import com.lion.mvvmlib.util.EventType
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class ProjectFragment : BaseFragment<ProjectViewModel, FragmentProjectBinding>() {

    override fun layoutId() = R.layout.fragment_project

    override fun initView(savedInstanceState: Bundle?) {
        mBinding?.viewModel = mViewModel
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun lazyLoadData() {
        mViewModel.getFirstData()
    }

    override fun handleEvent(event: EventItem) {
        when (event.type) {
            EventType.EVENT_MSG -> {
                val bean = event.obj as ArticlesBean
                val intent = Intent(context, WebviewActivity::class.java)
                intent.putExtra(EXTRA_URL, bean.link)
                startActivity(intent)
            }
        }
    }
}