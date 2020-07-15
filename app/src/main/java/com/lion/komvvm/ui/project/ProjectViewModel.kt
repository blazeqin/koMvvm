package com.lion.komvvm.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils
import com.lion.komvvm.entity.ArticlesBean
import com.lion.mvvmlib.base.BaseVM

class ProjectViewModel : BaseVM() {

    private val mItemClick = object : OnItemClickListener {
        override fun onItemClick(item: ArticlesBean) {
            LogUtils.i("item click: ${item.author}")
        }
    }
    interface OnItemClickListener{
        fun onItemClick(item: ArticlesBean)
    }
}