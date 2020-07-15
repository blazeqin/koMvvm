package com.lion.mvvmlib.base

data class BaseResult<out T>(
    val errCode:Int,
    val errMsg:String,
    val data: T
) {
    fun isSuccess() = errCode == 0
}