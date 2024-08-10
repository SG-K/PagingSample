package com.example.pagingsample.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Posts"
)
data class Post(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val description: String,
)
