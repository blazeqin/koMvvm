package com.lion.komvvm.data

import com.lion.komvvm.network.HomeNetwork
import com.lion.komvvm.network.ProjectNetwork
import com.lion.komvvm.utils.SingletonHolder

/**
 * can use three layer store cache in this class
 */
class ProjectRepository private constructor(
    private val network: ProjectNetwork
){
    suspend fun getTabData() = network.getTabData()

    suspend fun getProjectList(page: Int, cid: Int) = network.getProjectList(page, cid)

    companion object: SingletonHolder<ProjectRepository, ProjectNetwork>(::ProjectRepository)
}