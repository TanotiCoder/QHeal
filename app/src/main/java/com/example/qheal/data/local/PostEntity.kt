package com.example.qheal.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "postEntity")
data class PostEntity(
    @PrimaryKey
    val _id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val addedOn: String
)

