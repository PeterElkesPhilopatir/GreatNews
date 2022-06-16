package com.peter.greatnews.framework.datasource.network

import com.peter.greatnews.BuildConfig
import com.peter.greatnews.framework.datasource.Constants.PAGE_SIZE
import com.peter.greatnews.framework.datasource.responses.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("v2/everything")
    fun getNewsAsync(
        @Query("q") query: String,
        @Query("apiKey") apikey: String = BuildConfig.API_KEY,
        @Query("pageSize") perPage: Int = PAGE_SIZE,
        @Query("page") page: Int,
    ):
            Deferred<NewsResponse>
}