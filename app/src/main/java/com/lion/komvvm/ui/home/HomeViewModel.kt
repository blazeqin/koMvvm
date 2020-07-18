package com.lion.komvvm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.lion.komvvm.entity.BannerBean
import com.lion.komvvm.entity.HomeListBean
import com.lion.komvvm.utils.InjectorUtil
import com.lion.mvvmlib.base.BaseVM

class HomeViewModel : BaseVM() {

    //the data class
    private val mRepository by lazy { InjectorUtil.getHomeRepository() }
    //home list data live data: this maybe get multi-times once time.
    //but I do some limits when refreshing ui.
    val mDatas = MutableLiveData<HomeListBean>()
    val mBannerData = MutableLiveData<List<BannerBean>>()
    private var page = 0

    fun getHomeList(isMore: Boolean = false) {
        if (isMore.not()) page = 0

        launchFilterResponse(
            //request the data
            {mRepository.getHomeListData(page)},
            //request successfully
            {
                mDatas.value = it
                page = it.curPage
            }
        )
    }

    fun getBanner() {
        launchFilterResponse(
            {mRepository.getBannerData()},
            {mBannerData.value = it}
        )
    }
}