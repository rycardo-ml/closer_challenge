package com.closer.test.presentation.articles.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.closer.test.databinding.ArticleRowBinding
import com.closer.test.util.model.Article

class ArticleAdapter(val clickListener: (article: Article) -> Unit): PagingDataAdapter<Article, ArticleHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ArticleRowBinding.inflate(layoutInflater, parent, false)

        return ArticleHolder(binding) { clickListener.invoke(getItem(it)!!) }
    }
}