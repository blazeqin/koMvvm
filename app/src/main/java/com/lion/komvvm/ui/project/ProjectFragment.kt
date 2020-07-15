package com.lion.komvvm.ui.project

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.lion.komvvm.R
import com.lion.mvvmlib.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectFragment : BaseFragment<ProjectViewModel, ViewDataBinding>() {

    override fun layoutId() = R.layout.fragment_project

    override fun initView(savedInstanceState: Bundle?) {

    }
}