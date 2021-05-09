package com.closer.test.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.closer.test.presentation.articles.paging.ArticlePagingSource
import com.closer.test.util.model.Article
import com.closer.test.util.network.ArticleService
import kotlinx.coroutines.flow.Flow

private const val TAG = "ArticleRepository"
private const val DEFAULT_PAGE_SIZE = 10
class ArticleRepository(
    private val articleAPI: ArticleService
) {

    fun fetchArticlesFlow(): Flow<PagingData<Article>> {
        Log.d(TAG, "fetchArticlesFlow")
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory =  { ArticlePagingSource(articleAPI) }
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = DEFAULT_PAGE_SIZE
        )
    }
}