package com.example.pagingsample.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostsDao {

    @Query("SELECT * FROM Posts LIMIT :limit OFFSET :offset")
    suspend fun getPosts(
        limit: Int,
        offset: Int,
    ) : List<Post>

    @Query("SELECT * FROM Posts")
    suspend fun getAllPosts() : List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePosts(list: List<Post>)
}