package com.lion.komvvm.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserBean(
    val icon: String,
    @PrimaryKey
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)