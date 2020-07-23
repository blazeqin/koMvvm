package com.lion.komvvm.ui.project

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.google.android.material.tabs.TabLayout
import com.lion.komvvm.entity.ArticlesBean
import com.lion.komvvm.entity.NavTypeBean
import com.lion.komvvm.utils.InjectorUtil
import com.lion.mvvmlib.base.BaseVM
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class ProjectViewModel : BaseVM() {

    private val mRepository = InjectorUtil.getProjectRepository()
    private var page = 0

    //tab 使用databinding
    val mTabTitle = ObservableArrayList<String>()
    val mTabData = ObservableArrayList<NavTypeBean>()

    //data list
    val mDatas = MutableLiveData<MutableList<ArticlesBean>>()

//    val mItemBinding = ItemBinding.of<ArticlesBean>(BR.itemBean, R.layout.item_project_list)
//        .bindExtra(BR.listener, mItemClick)

    val mTabClickListener = object :TabLayout.OnTabSelectedListener{
        override fun onTabReselected(tab: TabLayout.Tab?) {
            LogUtils.i("onTabReselected")
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                LogUtils.i("onTabSelected :${it.position}")
                getProjectList(mTabData[it.position].id)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getFirstData() {
//        launchUI {
//    //在调用 flatMapConcat 后，collect 函数在收集新值之前会等待 flatMapConcat 内部的 flow 完成。
//            launchFlow { mRepository.getTabData() }
//                .flatMapConcat {
//                    return@flatMapConcat if (it.isSuccess()) {
//                        mTabData.addAll(it.data)
//                        it.data.forEach { item-> mTabTitle.add(item.name) }
//                        launchFlow { mRepository.getProjectList(page, it.data[0].id) }
//                    }else
//                        throw ResponseThrowable(it.errCode,it.errMsg)
//                }
//                .onStart { eventUI.setValue(EventItem(EventType.LOADING_SHOW)) }
//                .flowOn(Dispatchers.Main)
//                .onCompletion { eventUI.setValue(EventItem(EventType.LOADING_DISMISS)) }
//                .catch {
//                    val err = ExceptionHandle.handleException(it)
//                    LogUtils.d("${err.code}: ${err.msg}")
//                }
//                .collect {
//                    if (it.isSuccess()) mDatas.value = it.data.datas
//                }
//        }
        launchFilterResponse(
            {mRepository.getTabData()},
            {
                mTabData.addAll(it)
                it.forEach { item-> mTabTitle.add(item.name) }
            }
        )
    }

    fun getProjectList(cid: Int) {
        launchFilterResponse(
            {mRepository.getProjectList(page, cid)},
            {
                mDatas.value = it.datas
            }
        )
    }
}