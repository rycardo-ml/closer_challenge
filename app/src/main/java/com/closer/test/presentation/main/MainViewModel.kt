package com.closer.test.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.closer.test.util.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private val _ldArticle = MutableLiveData<Article?>()
    val ldArticle: LiveData<Article?> get() = _ldArticle

    fun setArticleSelected(article: Article) {
        _ldArticle.postValue(article)
    }

    fun resetSelectedArticle() {
        _ldArticle.postValue(null)
    }
}