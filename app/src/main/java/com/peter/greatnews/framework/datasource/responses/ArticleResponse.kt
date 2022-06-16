package com.peter.greatnews.framework.datasource.responses

data class ArticleResponse(
    var author: String?,
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?,
    var source: SourceResponse?
)
