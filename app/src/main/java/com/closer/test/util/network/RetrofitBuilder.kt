package com.closer.test.util.network

import android.util.Log
import com.closer.test.util.network.converter.ArticleConverter
import com.closer.test.util.network.converter.TypeTokenCreator
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val TAG = "RetrofitBuilder"
    private const val POSTAL_CODE_URL = "https://raw.githubusercontent.com/centraldedados/codigos_postais/"
    private const val ARTICLE_URL = "https://5bb1cd166418d70014071c8e.mockapi.io/"

    private fun getRetrofitPostalCode(): Retrofit {
        Log.d(TAG, "getRetrofitPostalCode")

        val gson =
            GsonBuilder()
                .create()

        return Retrofit.Builder()
            .baseUrl(POSTAL_CODE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiPostalCode: PostalCodeService by lazy {
        getRetrofitPostalCode().create(PostalCodeService::class.java)
    }

    private fun getRetrofitArticle(): Retrofit {
        Log.d(TAG, "getRetrofitArticle")

        val gson =
            GsonBuilder()
                .registerTypeAdapter(TypeTokenCreator.createListArticle(), ArticleConverter())
                .create()

        return Retrofit.Builder()
            .baseUrl(ARTICLE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiArticle: ArticleService by lazy {
        getRetrofitArticle().create(ArticleService::class.java)
    }


}