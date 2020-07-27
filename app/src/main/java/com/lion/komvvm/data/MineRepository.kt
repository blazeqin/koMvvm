package com.lion.komvvm.data

import android.content.Context
import com.lion.komvvm.entity.UserBean
import com.lion.komvvm.network.HomeNetwork
import com.lion.komvvm.network.MineNetwork
import com.lion.komvvm.network.ProjectNetwork
import com.lion.komvvm.utils.InjectorUtil
import com.lion.komvvm.utils.SingletonHolder
import com.lion.mvvmlib.base.BaseResult

/**
 * can use three layer store cache in this class
 */
class MineRepository private constructor(
    private val network: MineNetwork
){
    private val mUserDao by lazy { InjectorUtil.getUserDao() }

    suspend fun getMineData(): BaseResult<List<UserBean>> {
        val users = mUserDao.getUsers()
        if (users.isNullOrEmpty()) {
            val mineData = network.getMineData()
            if (mineData.isSuccess()) {
                mUserDao.insertAll(mineData.data)
            }
            return mineData
        }
        return BaseResult(0,"", users)
    }

    companion object: SingletonHolder<MineRepository, MineNetwork>(::MineRepository)
}