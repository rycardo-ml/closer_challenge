package com.closer.test.presentation.articles.adapter

import androidx.recyclerview.widget.RecyclerView
import com.closer.test.databinding.ArticleRowBinding
import com.closer.test.util.model.Article

class ArticleHolder(val binding: ArticleRowBinding, val clickListener: (position: Int) -> Unit): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            clickListener.invoke(bindingAdapterPosition)
        }
    }

    fun bind(item: Article) {
        binding.tvTitle.text = item.title
        binding.tvAuthor.text = item.author
        binding.tvDescription.text = item.summary
    }
}