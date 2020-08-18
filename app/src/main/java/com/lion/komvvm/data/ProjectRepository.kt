package com.lion.komvvm.data

import PAGE_SIZE
import android.content.Context
import com.lion.komvvm.data.dao.ArticleDao
import com.lion.komvvm.entity.ArticlesBean
import com.lion.komvvm.entity.HomeListBean
import com.lion.komvvm.entity.NavTypeBean
import com.lion.komvvm.network.ProjectNetwork
import com.lion.komvvm.utils.InjectorUtil
import com.lion.komvvm.utils.SingletonHolder
import com.lion.mvvmlib.base.BaseResult
import getCommonData
import kotlinx.coroutines.CoroutineScope
import mFlagMap

/**
 * can use three layer store cache in this class
 * 1 get data from Room.
 * 2 data is null, then get from network
 */
class ProjectRepository private constructor(
    private val network: ProjectNetwork
){
    //get data from room
    private val mTabDao by lazy { InjectorUtil.getTabDao() }
    private val mArticleDao by lazy { InjectorUtil.getArticleDao() }

    suspend fun getTabData(scope: CoroutineScope): BaseResult<List<NavTypeBean>>{
        //get from room first, if null, then get from network, and store the data to room
        return getCommonData(
            scope,mTabDao,
            dbData = {mTabDao.getTabs()},
            networkData = {network.getTabData()},
            insertData = {mTabDao.insertAll(it)}
        )
    }

    suspend fun getProjectList(page: Int, cid: Int): BaseResult<HomeListBean> {
        //first load from internet
        val flag = mFlagMap.get(mArticleDao)
        var result: List<ArticlesBean>? = null
        if (flag == null || !flag) {
            mFlagMap[mArticleDao] = true
        }else
            result = mArticleDao.getArticlesById(PAGE_SIZE, page*PAGE_SIZE, cid)

        if (result.isNullOrEmpty()) {
            val homeListData = network.getProjectList(page, cid)
            if (homeListData.isSuccess()) {
                mArticleDao.insertAll(homeListData.data.datas)
            }
            return homeListData
        }
        return BaseResult(0, "", HomeListBean(page+1,result.toMutableList()))
    }

    companion object: SingletonHolder<ProjectRepository, ProjectNetwork>(::ProjectRepository)
}