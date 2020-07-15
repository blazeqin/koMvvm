package com.lion.komvvm.data

import com.lion.komvvm.network.HomeNetwork
import com.lion.komvvm.utils.SingletonHolder

/**
 * can use three layer store cache in this class
 */
class HomeRepository private constructor(
    private val network: HomeNetwork
){
    suspend fun getBannerData() = network.getBannerData()

    suspend fun getHomeListData(page: Int) = network.getHomeListData(page)

    companion object: SingletonHolder<HomeRepository, HomeNetwork>(::HomeRepository)
}