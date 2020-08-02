package com.lion.komvvm.data

import android.content.Context
import com.lion.komvvm.entity.UserBean
import com.lion.komvvm.network.HomeNetwork
import com.lion.komvvm.network.MineNetwork
import com.lion.komvvm.network.ProjectNetwork
import com.lion.komvvm.utils.InjectorUtil
import com.lion.komvvm.utils.SingletonHolder
import com.lion.mvvmlib.base.BaseResult
import getCommonData
import kotlinx.coroutines.CoroutineScope

/**
 * can use three layer store cache in this class
 */
class MineRepository private constructor(
    private val network: MineNetwork
){
    private val mUserDao by lazy { InjectorUtil.getUserDao() }

    suspend fun getMineData(scope: CoroutineScope): BaseResult<List<UserBean>> {
        return getCommonData(
            scope,
            dbData = {mUserDao.getUsers()},
            networkData = {network.getMineData()},
            insertData = {mUserDao.insertAll(it)}
        )
    }

    companion object: SingletonHolder<MineRepository, MineNetwork>(::MineRepository)
}