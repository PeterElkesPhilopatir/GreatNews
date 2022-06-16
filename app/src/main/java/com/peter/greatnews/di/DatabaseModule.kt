package com.peter.greatnews.di

import android.content.Context
import androidx.room.Room
import com.peter.greatnews.framework.database.NewsDao
import com.peter.greatnews.framework.database.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDao(db: NewsDatabase): NewsDao = db.dao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NewsDatabase =
        Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_db"
        )
            .fallbackToDestructiveMigration()
            .build()
}