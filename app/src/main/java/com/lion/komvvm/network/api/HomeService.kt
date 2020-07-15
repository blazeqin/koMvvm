package com.lion.komvvm.network.api

import com.lion.komvvm.entity.BannerBean
import com.lion.komvvm.entity.HomeListBean
import com.lion.mvvmlib.base.BaseResult
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {
    //banner
    @GET("banner/json")
    suspend fun getBanner(): BaseResult<List<BannerBean>>

    //home list data
    @GET("article/listproject/{page}/json")
    suspend fun getHomeList(@Path("page") page: Int): BaseResult<HomeListBean>
}