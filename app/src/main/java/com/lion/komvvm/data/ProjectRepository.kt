package com.lion.komvvm.data

import android.content.Context
import com.lion.komvvm.entity.NavTypeBean
import com.lion.komvvm.network.ProjectNetwork
import com.lion.komvvm.utils.SingletonHolder
import com.lion.mvvmlib.base.BaseResult

/**
 * can use three layer store cache in this class
 * 1 get data from Room.
 * 2 data is null, then get from network
 */
class ProjectRepository private constructor(
    private val context: Context
){
    //get data from room
    private val mTabDao by lazy { AppDatabase.getInstance(context.applicationContext).tabDao() }
    //get data from network
    private val network by lazy { ProjectNetwork.sInstance }

    suspend fun getTabData(): BaseResult<List<NavTypeBean>>{
        //get from room first, if null, then get from network, and store the data to room
        val result:List<NavTypeBean>? = mTabDao.getTabs()
        if (result.isNullOrEmpty()) {
            val tabData = network.getTabData()
            if (tabData.errCode == 0) {
                mTabDao.insertAll(tabData.data)
            }
            return tabData
        }
        return BaseResult(0, "",result)
    }

    suspend fun getProjectList(page: Int, cid: Int) = network.getProjectList(page, cid)

    companion object: SingletonHolder<ProjectRepository, Context>(::ProjectRepository)
}