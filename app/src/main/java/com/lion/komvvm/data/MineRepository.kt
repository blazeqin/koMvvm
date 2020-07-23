package com.lion.komvvm.data

import com.lion.komvvm.network.HomeNetwork
import com.lion.komvvm.network.MineNetwork
import com.lion.komvvm.network.ProjectNetwork
import com.lion.komvvm.utils.SingletonHolder

/**
 * can use three layer store cache in this class
 */
class MineRepository private constructor(
    private val network: MineNetwork
){
    suspend fun getMineData() = network.getMineData()

    companion object: SingletonHolder<MineRepository, MineNetwork>(::MineRepository)
}