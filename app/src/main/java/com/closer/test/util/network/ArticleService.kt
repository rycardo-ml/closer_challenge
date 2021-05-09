package com.closer.test.util.network

import com.closer.test.util.model.Article
import retrofit2.http.GET
import retrofit2.http.Query

//https://5bb1cd166418d70014071c8e.mockapi.io/mobile/1-1/articles?page=1&limit=10
interface ArticleService {

    @GET("mobile/1-1/articles")
    suspend fun fetchArticles(@Query("page") page: Int, @Query("limit") size: Int): List<Article>

}