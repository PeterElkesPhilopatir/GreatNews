package com.peter.greatnews.framework.database

import androidx.room.*
import com.peter.greatnews.business.model.Article

@Dao
interface NewsDao {
    @Query("SELECT * FROM article_tbl ")
    fun getAll(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Delete
    fun delete(article: Article)
}