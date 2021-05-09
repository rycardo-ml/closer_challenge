package com.closer.test.presentation.main.articles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.closer.test.repository.ArticleRepository
import com.closer.test.util.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "ArticlesViewModel"

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    fun fetchArticles(): Flow<PagingData<Article>> {
        Log.d(TAG, "fetchArticles")
        return articleRepository.fetchArticlesFlow()
    }
}
