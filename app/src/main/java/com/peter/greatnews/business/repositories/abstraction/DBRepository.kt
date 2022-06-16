package com.peter.greatnews.business.repositories.abstraction

import com.peter.greatnews.business.model.Article
import kotlinx.coroutines.flow.Flow

interface DBRepository {
    fun getFavoriteNews(): Flow<List<Article>>
    fun isFavorite(article: Article): Flow<Boolean>
    fun saveToFavorite(article: Article): Flow<Boolean>
}