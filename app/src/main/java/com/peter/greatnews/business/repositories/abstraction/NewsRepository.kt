package com.peter.greatnews.business.repositories.abstraction

import com.peter.greatnews.business.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(query: String,page : Int): Flow<List<Article>>
}