package com.example.pagingsample.di

import android.app.Application
import androidx.room.Room
import com.example.pagingsample.data.database.PostsDatabase
import com.example.pagingsample.data.repository.PostsRepositoryImpl
import com.example.pagingsample.domain.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(
        app: Application
    ): PostsDatabase {
        return Room.databaseBuilder(
            app,
            PostsDatabase::class.java,
            PostsDatabase.DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun providePostsRepository(
        postsDatabase: PostsDatabase
    ): PostsRepository {
        return PostsRepositoryImpl(postsDatabase.postsDao)
    }
}