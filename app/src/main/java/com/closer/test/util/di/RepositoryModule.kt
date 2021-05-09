package com.closer.test.util.di

import com.closer.test.repository.ArticleRepository
import com.closer.test.repository.PostalCodeRepository
import com.closer.test.util.database.AppDatabase
import com.closer.test.util.network.ArticleService
import com.closer.test.util.network.PostalCodeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @ActivityRetainedScoped
    @Provides
    fun providePostalCode(postalCodeService: PostalCodeService, db: AppDatabase) =
        PostalCodeRepository(postalCodeService, db)

    @ActivityRetainedScoped
    @Provides
    fun provideArticle(articleAPI: ArticleService) =
        ArticleRepository(articleAPI)
}