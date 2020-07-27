package com.lion.komvvm.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lion.komvvm.entity.NavTypeBean

/**
 * The Data Access Object for the NavTypeBean class.
 */
@Dao
interface TabDao :BaseDao<List<NavTypeBean>>{

    //cannot use suspend with livedata
    @Query("SELECT * FROM tabs ORDER BY id")
    suspend fun getTabs(): List<NavTypeBean>

}