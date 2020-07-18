package com.lion.komvvm.utils

import com.lion.komvvm.data.HomeRepository
import com.lion.komvvm.data.ProjectRepository
import com.lion.komvvm.network.HomeNetwork
import com.lion.komvvm.network.ProjectNetwork

object InjectorUtil {
    /**
     * get the relative data about home fragment
     */
    fun getHomeRepository() = HomeRepository.getInstance(HomeNetwork.sInstance)

    fun getProjectRepository() = ProjectRepository.getInstance(ProjectNetwork.sInstance)
}
