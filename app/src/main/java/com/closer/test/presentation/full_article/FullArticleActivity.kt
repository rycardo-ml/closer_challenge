package com.closer.test.presentation.full_article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.closer.test.databinding.FullArticleActivityBinding
import com.closer.test.util.model.Article

private const val ARTICLE_KEY = "ARTICLE"

class FullArticleActivity : AppCompatActivity() {

    companion object {
        fun createIntent(article: Article) = Intent().apply {
            putExtra(ARTICLE_KEY, article)
        }
    }

    private var _binding: FullArticleActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = FullArticleActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.getParcelableExtra<Article>(ARTICLE_KEY)!!

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, FullArticleFragment.newInstance(article))
                .commitNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
