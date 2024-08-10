package com.example.pagingsample.domain

import com.example.pagingsample.data.database.Post

interface PostsRepository {

    suspend fun getPosts(
        limit: Int,
        offset: Int,
    ) : Result<List<Post>>

    suspend fun getAllPosts() : List<Post>

    suspend fun savePosts(list: List<Post>)
}