package com.lion.mvvmlib.base

import com.lion.mvvmlib.util.EventType

/**
 * 事件的item
 */
data class EventItem (
    val type: EventType,//事件类型
    var obj: Any? =null//数据
)