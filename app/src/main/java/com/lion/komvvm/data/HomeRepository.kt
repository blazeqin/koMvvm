package com.lion.komvvm.data

import PAGE_SIZE
import android.content.Context
import com.lion.komvvm.entity.BannerBean
import com.lion.komvvm.entity.HomeListBean
import com.lion.komvvm.network.HomeNetwork
import com.lion.komvvm.utils.InjectorUtil
import com.lion.komvvm.utils.SingletonHolder
import com.lion.mvvmlib.base.BaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

/**
 * can use three layer store cache in this class
 */
class HomeRepository private constructor(
    private val network: HomeNetwork
){
    private val mArticleDao by lazy { InjectorUtil.getArticleDao()}
    private val mBannerDao by lazy { InjectorUtil.getBannerDao()}

    suspend fun getBannerData(): BaseResult<List<BannerBean>> {
        val banners = mBannerDao.getBanners()
        if (banners.isNullOrEmpty()) {
            val bannerData = network.getBannerData()
            if (bannerData.isSuccess()) {
                mBannerDao.insertAll(bannerData.data)
            }
            return bannerData
        }
        return BaseResult(0,"", banners)
    }

    suspend fun getHomeListData(page: Int): BaseResult<HomeListBean> {
        //get from room, is null, then get from network
        val result = mArticleDao.getArticles(PAGE_SIZE, page*PAGE_SIZE)
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