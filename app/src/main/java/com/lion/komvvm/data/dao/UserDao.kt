package com.lion.komvvm.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.lion.komvvm.entity.UserBean
@Dao
interface UserDao : BaseDao<List<UserBean>> {

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserBean>
}