package com.lion.komvvm.network

import com.lion.komvvm.network.api.MineService
import com.lion.komvvm.network.api.ProjectService

/**
 * this layer is only to connect with network
 */
class MineNetwork private constructor(){
    private val mService by lazy { RetrofitClient.getInstance().create(MineService::class.java) }

    suspend fun getMineData() = mService.getMineData()

    companion object{
        //this lazy way can promise to create a safety single instance
        val sInstance by lazy { MineNetwork() }
    }
}