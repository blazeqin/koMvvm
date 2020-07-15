package com.lion.mvvmlib.util

/**
 * 事件类型： 编号从0开始
 * 获取名称和编号： name, ordinal
 */
enum class EventType{
    LOADING_SHOW,
    LOADING_DISMISS,
    EVENT_TOAST,
    EVENT_MSG
}