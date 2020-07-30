package com.lion.komvvm.ui.project

import androidx.lifecycle.MutableLiveData
import com.lion.komvvm.entity.NavTypeBean
import com.lion.komvvm.utils.InjectorUtil
import com.lion.mvvmlib.base.BaseVM

class ProjectViewModel : BaseVM() {
    private val mRepository = InjectorUtil.getProjectRepository()
    val mTabTitle = MutableLiveData<List<NavTypeBean>>()

    fun getFirstData() {
        launchFilterResponse(
            {mRepository.getTabData()},
            {
                mTabTitle.value = it
            }
        )
    }

}