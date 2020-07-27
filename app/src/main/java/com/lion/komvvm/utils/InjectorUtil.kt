package com.lion.komvvm.utils

import android.content.Context
import com.blankj.utilcode.util.Utils
import com.lion.komvvm.data.AppDatabase
import com.lion.komvvm.data.HomeRepository
import com.lion.komvvm.data.MineRepository
import com.lion.komvvm.data.ProjectRepository
import com.lion.komvvm.network.HomeNetwork
import com.lion.komvvm.network.MineNetwork
import com.lion.komvvm.network.ProjectNetwork

object InjectorUtil {
    /**
     * get the relative data about home fragment
     */
    private val context by lazy { Utils.getApp() }

    fun getHomeRepository() = HomeRepository.getInstance(HomeNetwork.sInstance)

    fun getProjectRepository() = ProjectRepository.getInstance(ProjectNetwork.sInstance)

    fun getMineRepository() = MineRepository.getInstance(MineNetwork.sInstance)

    fun getBannerDao() = AppDatabase.getInstance(context.applicationContext).bannerDao()
    fun getArticleDao() = AppDatabase.getInstance(context.applicationContext).articleDao()
    fun getTabDao() = AppDatabase.getInstance(context.applicationContext).tabDao()
    fun getUserDao() = AppDatabase.getInstance(context.applicationContext).userDao()
}
