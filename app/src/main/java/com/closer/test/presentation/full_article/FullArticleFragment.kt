package com.closer.test.presentation.full_article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.closer.test.R
import com.closer.test.databinding.FullArticleFragmentBinding
import com.closer.test.util.model.Article
import java.text.SimpleDateFormat
import java.util.*

private const val ARTICLE_KEY = "ARTICLE"

class FullArticleFragment : Fragment() {

    companion object {
        fun newInstance(article: Article) = FullArticleFragment().apply {
            arguments = Bundle().apply {
                this.putParcelable(ARTICLE_KEY, article)
            }
        }
    }

    private var _binding: FullArticleFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FullArticleFragmentBinding.inflate(inflater, container, false)

        setupData()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupData() {
        arguments.let {
            if (it == null) {
                Toast.makeText(context, "Failed to find article", Toast.LENGTH_LONG).show()
                requireActivity().finish()
                return@let
            }

            setupArticle(it.getParcelable(ARTICLE_KEY)!!)
        }
    }

    private fun setupArticle(article: Article) {
        val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

        binding.tvTitle.text = article.title
        binding.tvAuthor.text = article.author
        binding.tvPublishedDate.text = sdf.format(article.publishedAt)
        binding.tvDescription.text = article.body


        article.hero.let {
            if (it == null) {
                binding.ivHeader.visibility = View.GONE
                return
            }

            binding.ivHeader.visibility = View.VISIBLE
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error_connection)
                .into(binding.ivHeader)
        }

        binding.root.post {
            binding.root.scrollY = 300
        }
    }
}
