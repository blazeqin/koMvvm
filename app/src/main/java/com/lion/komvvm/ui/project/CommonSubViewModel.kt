package com.lion.komvvm.ui.project

import androidx.lifecycle.MutableLiveData
import com.lion.komvvm.entity.ArticlesBean
import com.lion.komvvm.utils.InjectorUtil
import com.lion.mvvmlib.base.BaseVM

class CommonSubViewModel:BaseVM() {

    private val mRepository = InjectorUtil.getProjectRepository()
    private var page = 0

    //data list
    val mDatas = MutableLiveData<MutableList<ArticlesBean>>()

    fun getProjectList(cid: Int) {
        launchFilterResponse(
            {mRepository.getProjectList(page, cid)},
            {
                mDatas.value = it.datas
            }
        )
    }
}
