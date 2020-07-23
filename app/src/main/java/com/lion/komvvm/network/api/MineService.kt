package com.lion.komvvm.network.api

import com.lion.komvvm.entity.UserBean
import com.lion.mvvmlib.base.BaseResult
import retrofit2.http.GET

interface MineService {
    /**
     * 常用网站
     */
    @GET("friend/json")
    suspend fun getMineData(): BaseResult<MutableList<UserBean>>
}