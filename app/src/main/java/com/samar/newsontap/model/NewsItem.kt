package com.samar.newsontap.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val status: String,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    @SerializedName("urlToImage")
    val imageUrl: String?,
    val publishedAt: String,
    val content: String?
)

data class Source(
    val id: String?,
    val name: String
)
