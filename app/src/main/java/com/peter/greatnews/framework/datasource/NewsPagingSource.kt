package com.peter.greatnews.framework.datasource

import androidx.paging.PagingSource
import com.peter.greatnews.business.model.Article
import com.peter.greatnews.business.usecases.abstraction.NewsUseCases
import kotlinx.coroutines.flow.collectLatest
import java.io.IOException

const val START_PAGE = 1

class NewsPagingSource(
    private val useCase: NewsUseCases,
    private val query: String
) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: START_PAGE
        var list = ArrayList<Article>()
        return try {
            useCase.getNews(query, position)
                .collectLatest {
                    list = it as ArrayList<Article>
                }
            LoadResult.Page(
                data = list,
                prevKey = if (position == START_PAGE) null else position - 1,
                nextKey = if (list.isEmpty()) null else position + 1
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

}