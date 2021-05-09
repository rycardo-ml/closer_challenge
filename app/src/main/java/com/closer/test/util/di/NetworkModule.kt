package com.closer.test.util.di

import com.closer.test.util.network.ArticleService
import com.closer.test.util.network.PostalCodeService
import com.closer.test.util.network.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providePostalCodeApi(): PostalCodeService = RetrofitBuilder.apiPostalCode

    @Singleton
    @Provides
    fun provideArticleApi(): ArticleService = RetrofitBuilder.apiArticle
}