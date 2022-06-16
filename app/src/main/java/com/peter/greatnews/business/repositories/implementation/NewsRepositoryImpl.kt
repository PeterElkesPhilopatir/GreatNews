package com.peter.greatnews.business.repositories.implementation

import android.util.Log
import com.peter.greatnews.BuildConfig
import com.peter.greatnews.business.fromResponseToModel
import com.peter.greatnews.business.model.Article
import com.peter.greatnews.business.repositories.abstraction.NewsRepository
import com.peter.greatnews.framework.datasource.network.NewsAPI
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val api: NewsAPI) : NewsRepository {
    override fun getNews(query: String, page: Int): Flow<List<Article>> = flow {
        try {
            val response =
                api.getNewsAsync(query = query, page = page).await()
            emit(response.fromResponseToModel())
        } catch (e: Exception) {
            Log.e("articles_error", e.message.toString())
            e.printStackTrace()
            emit(listOf())
        }
    }.flowOn(IO)
}