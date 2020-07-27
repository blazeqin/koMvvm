package com.lion.komvvm.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.lion.komvvm.entity.BannerBean

@Dao
interface BannerDao : BaseDao<List<BannerBean>> {

    @Query("SELECT * FROM banners")
    suspend fun getBanners(): List<BannerBean>
}