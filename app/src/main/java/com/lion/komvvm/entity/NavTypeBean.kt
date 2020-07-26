package com.lion.komvvm.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "tabs")
data class NavTypeBean(
    @Ignore val children: List<Any>?,
    @ColumnInfo(name="course_id") var courseId: Int,
    @PrimaryKey @ColumnInfo(name = "id")
    var id: Int,
    var name: String,
    var order: Int,
    @ColumnInfo(name="parent_chapter_id") var parentChapterId: Int,
    @ColumnInfo(name="user_control_set_top") var userControlSetTop: Boolean,
    var visible: Int
){
    constructor() : this(null,0,0,"", 0,0,false,0)
}