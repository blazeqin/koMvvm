package com.lion.komvvm.data

import PAGE_SIZE
import com.lion.komvvm.entity.ArticlesBean
import com.lion.komvvm.entity.BannerBean
import com.lion.komvvm.entity.HomeListBean
import com.lion.komvvm.network.HomeNetwork
import com.lion.komvvm.utils.InjectorUtil
import com.lion.komvvm.utils.SingletonHolder
import com.lion.mvvmlib.base.BaseResult
import getCommonData
import kotlinx.coroutines.CoroutineScope
import mFlagMap

/**
 * can use three layer store cache in this class
 */
class HomeRepository private constructor(
    private val network: HomeNetwork
){
    private val mArticleDao by lazy { InjectorUtil.getArticleDao()}
    private val mBannerDao by lazy { InjectorUtil.getBannerDao()}

    suspend fun getBannerData(scope: CoroutineScope): BaseResult<List<BannerBean>> {
        return getCommonData(scope,mBannerDao,
        dbData = {mBannerDao.getBanners()},
        networkData = {network.getBannerData()},
        insertData = {mBannerDao.insertAll(it)}
        )
    }

    suspend fun getHomeListData(page: Int): BaseResult<HomeListBean> {
        //first load from internet
        val flag = mFlagMap.get(mArticleDao)
        var result: List<ArticlesBean>? = null
        if (flag == null || !flag) {
            mFlagMap[mArticleDao] = true
        }else
            result = mArticleDao.getArticles(PAGE_SIZE, page*PAGE_SIZE)
        if (result.isNullOrEmpty()) {
            val homeListData = network.getHomeListData(page)
            if (homeListData.isSuccess()) {
                mArticleDao.insertAll(homeListData.data.datas)
            }
            return homeListData
        }
        return BaseResult(0, "", HomeListBean(page+1,result.toMutableList()))
    }

    companion object: SingletonHolder<HomeRepository, HomeNetwork>(::HomeRepository)
}