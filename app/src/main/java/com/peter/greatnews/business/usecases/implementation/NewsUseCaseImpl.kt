package com.peter.greatnews.business.usecases.implementation

import com.peter.greatnews.business.model.Article
import com.peter.greatnews.business.repositories.abstraction.DBRepository
import com.peter.greatnews.business.repositories.abstraction.NewsRepository
import com.peter.greatnews.business.usecases.abstraction.NewsUseCases
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCaseImpl @Inject constructor(
    private val repository: NewsRepository,
    private val dbRepository: DBRepository
) : NewsUseCases {
    override fun getNews(query: String, page: Int): Flow<List<Article>> =
        repository.getNews(query, page)

    override fun getFavoriteNews(): Flow<List<Article>> = dbRepository.getFavoriteNews()

    override fun isFavorite(article: Article): Flow<Boolean> = dbRepository.isFavorite(article)

    override fun saveToFavorite(article: Article): Flow<Boolean> =
        dbRepository.saveToFavorite(article)
}