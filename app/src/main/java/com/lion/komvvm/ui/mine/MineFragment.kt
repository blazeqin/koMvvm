package com.lion.komvvm.ui.mine

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.lion.komvvm.R
import com.lion.mvvmlib.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment<MineViewModel, ViewDataBinding>() {

    override fun layoutId() = R.layout.fragment_mine

    override fun initView(savedInstanceState: Bundle?) {
        mViewModel.text.observe(this, Observer {
            text_notifications.text = it
        })
    }
}