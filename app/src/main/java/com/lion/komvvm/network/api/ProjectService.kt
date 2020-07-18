package com.lion.komvvm.network.api

import com.lion.komvvm.entity.BannerBean
import com.lion.komvvm.entity.HomeListBean
import com.lion.komvvm.entity.NavTypeBean
import com.lion.mvvmlib.base.BaseResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectService {
    /**
     * tab data
     */
    @GET("project/tree/json")
    suspend fun getTabData(): BaseResult<List<NavTypeBean>>

    //project list
    @GET("project/list/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int, @Query("cid") cid: Int): BaseResult<HomeListBean>

}