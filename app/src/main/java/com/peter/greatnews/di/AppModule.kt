package com.peter.greatnews.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.peter.greatnews.business.repositories.abstraction.DBRepository
import com.peter.greatnews.business.repositories.abstraction.NewsRepository
import com.peter.greatnews.business.repositories.implementation.DBRepositoryImpl
import com.peter.greatnews.business.repositories.implementation.NewsRepositoryImpl
import com.peter.greatnews.business.usecases.abstraction.NewsUseCases
import com.peter.greatnews.business.usecases.implementation.NewsUseCaseImpl
import com.peter.greatnews.framework.database.NewsDao
import com.peter.greatnews.framework.datasource.Constants.BASE_URL
import com.peter.greatnews.framework.datasource.network.NewsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideApiService(retrofit: Retrofit): NewsAPI = retrofit.create(NewsAPI::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

    @Singleton
    @Provides
    fun provideNewsRepository(api: NewsAPI): NewsRepository = NewsRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideDBRepository(dao: NewsDao): DBRepository = DBRepositoryImpl(dao)

    @Singleton
    @Provides
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        dbRepository: DBRepository
    ): NewsUseCases = NewsUseCaseImpl(newsRepository, dbRepository)
}