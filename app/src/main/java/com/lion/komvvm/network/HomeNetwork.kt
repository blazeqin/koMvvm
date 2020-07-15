package com.lion.komvvm.network

import com.lion.komvvm.network.api.HomeService
/**
 * this layer is only to connect with network
 */
class HomeNetwork private constructor(){
    private val mService by lazy { RetrofitClient.getInstance().create(HomeService::class.java) }

    suspend fun getBannerData() = mService.getBanner()

    suspend fun getHomeListData(page: Int) = mService.getHomeList(page)

    companion object{
        //this lazy way can promise to create a safety single instance
        val sInstance by lazy { HomeNetwork() }
    }
}