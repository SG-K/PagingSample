package com.example.pagingsample.data.repository

import com.example.pagingsample.data.database.Post
import com.example.pagingsample.data.database.PostsDao
import com.example.pagingsample.domain.PostsRepository

class PostsRepositoryImpl(
    private val postsDao: PostsDao
): PostsRepository {
    override suspend fun getPosts(
        limit: Int,
        offset: Int,
    ): Result<List<Post>> {
        return try {
            Result.success(postsDao.getPosts(limit, offset))
        } catch ( e: Exception ) {
            Result.failure(e)
        }
    }

    override suspend fun getAllPosts() : List<Post> {
        return postsDao.getAllPosts()
    }

    override suspend fun savePosts(list: List<Post>) {
        postsDao.savePosts(list)
    }
}