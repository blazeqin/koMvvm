package com.lion.komvvm.utils

import com.lion.komvvm.data.HomeRepository
import com.lion.komvvm.network.HomeNetwork

object InjectorUtil {
    /**
     * get the relative data about home fragment
     */
    fun getHomeRepository() = HomeRepository.getInstance(HomeNetwork.sInstance)
}