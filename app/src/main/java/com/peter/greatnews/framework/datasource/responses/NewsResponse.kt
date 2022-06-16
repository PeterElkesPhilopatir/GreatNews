package com.peter.greatnews.framework.datasource.responses

data class NewsResponse(
    var articles: List<ArticleResponse>,
    var status: String,
    var totalResults: Int
)
