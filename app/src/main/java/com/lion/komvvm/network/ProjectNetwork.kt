package com.lion.komvvm.network

import com.lion.komvvm.entity.NavTypeBean
import com.lion.komvvm.network.api.ProjectService
import com.lion.mvvmlib.base.BaseResult

/**
 * this layer is only to connect with network
 */
class ProjectNetwork private constructor(){
    private val mService by lazy { RetrofitClient.getInstance().create(ProjectService::class.java) }

    suspend fun getTabData() = mService.getTabData()

    suspend fun getProjectList(page: Int, cid: Int) = mService.getProjectList(page, cid)

    companion object{
        //this lazy way can promise to create a safety single instance
        val sInstance by lazy { ProjectNetwork() }
    }
}