package com.peter.greatnews.business.repositories.implementation

import com.peter.greatnews.business.model.Article
import com.peter.greatnews.business.repositories.abstraction.DBRepository
import com.peter.greatnews.framework.database.NewsDao
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DBRepositoryImpl @Inject constructor(private val dao: NewsDao) : DBRepository {
    override fun getFavoriteNews(): Flow<List<Article>> =
        flow<List<Article>> { emit(dao.getAll()) }.flowOn(IO)

    override fun isFavorite(article: Article): Flow<Boolean> =
        flow {
            val data = dao.getAll()
            if (article in data)
                emit(true)
            else emit(false)

        }.flowOn(IO)

    override fun saveToFavorite(article: Article): Flow<Boolean> = flow {
        try {
            dao.insert(article)
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }.flowOn(IO)

}