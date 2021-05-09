package com.closer.test.presentation.main.articles.paging

import android.util.Log
import androidx.paging.PagingSource
import com.closer.test.util.model.Article
import com.closer.test.util.network.ArticleAPI
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "ArticlePagingSource"
private const val DEFAULT_PAGE_INDEX = 1
class ArticlePagingSource(private val articleAPI: ArticleAPI): PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        Log.d(TAG, "load")

        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX

        Log.d(TAG, "page[$page] size[${params.loadSize}]")

        return try {
            val response = articleAPI.fetchArticles(page, params.loadSize)

            LoadResult.Page(
                response,
                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            exception.printStackTrace()
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            exception.printStackTrace()
            LoadResult.Error(exception)
        }
    }
}