package com.closer.test.util.model

import com.google.gson.annotations.SerializedName

data class Article(
    val id: Long,
    val title: String,

    @SerializedName("published-at")
    val publishedAt: String,

    val hero: String,
    val author: String,
    val summary: String,
    val body: String,
)