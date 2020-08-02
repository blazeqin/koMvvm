package com.lion.komvvm.ui.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lion.komvvm.entity.UserBean
import com.lion.komvvm.utils.InjectorUtil
import com.lion.mvvmlib.base.BaseVM

class MineViewModel : BaseVM() {
    private val mRepository = InjectorUtil.getMineRepository()

    val mDatas = MutableLiveData<MutableList<Any>>()

    fun getMineData() {
        launchFilterResponse(
            {mRepository.getMineData(this)},
            {
                val datas = ArrayList<Any>()
                datas.add("Mine Title")
                datas.addAll(it)
                mDatas.value = datas
            }
        )
    }
}