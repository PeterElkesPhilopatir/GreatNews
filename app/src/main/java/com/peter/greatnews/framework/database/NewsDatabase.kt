package com.peter.greatnews.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peter.greatnews.business.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)

abstract class NewsDatabase : RoomDatabase() {
    abstract fun dao(): NewsDao
}



