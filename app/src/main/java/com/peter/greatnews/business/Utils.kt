package com.peter.greatnews.business

import com.peter.greatnews.business.model.Article
import com.peter.greatnews.framework.datasource.responses.ArticleResponse
import com.peter.greatnews.framework.datasource.responses.NewsResponse

fun NewsResponse.fromResponseToModel(): List<Article> {
    val list = mutableListOf<Article>()

    this.articles.forEach {

        list.add(
            Article(
                image = it.urlToImage?:"",
                author = it.author?:"",
                title = it.title?:"",
                description = it.description?:"",
                url = it.url?:"",
                date = it.publishedAt?:"",
                content = it.content?:""
            )
        )


    }
    return list
}