package com.example.pagingsample.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Post::class,
    ],
    version = 1
)
abstract class PostsDatabase : RoomDatabase() {

    abstract val postsDao: PostsDao

    companion object {
        const val DATABASE_NAME = "posts_db"
    }
}