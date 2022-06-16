package com.peter.greatnews.business.usecases.abstraction

import com.peter.greatnews.business.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsUseCases {
    fun getNews(query: String, page: Int): Flow<List<Article>>
    fun getFavoriteNews(): Flow<List<Article>>
    fun isFavorite(article: Article): Flow<Boolean>
    fun saveToFavorite(article: Article): Flow<Boolean>
}