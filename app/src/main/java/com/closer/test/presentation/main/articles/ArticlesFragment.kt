package com.closer.test.presentation.main.articles

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.closer.test.databinding.ArticlesFragmentBinding
import com.closer.test.presentation.main.articles.adapter.ArticleAdapter
import com.closer.test.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

private const val TAG = "ArticlesFragment"
//https://medium.com/swlh/paging3-recyclerview-pagination-made-easy-333c7dfa8797

@AndroidEntryPoint
class ArticlesFragment : Fragment() {

    companion object {
        fun newInstance() = ArticlesFragment()
    }

    private var _binding: ArticlesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticleAdapter

    private val viewModel by viewModels<ArticlesViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ArticlesFragmentBinding.inflate(inflater, container, false)

        Log.d(TAG, "viewModel $viewModel")
        Log.d(TAG, "viewModelMain $mainViewModel")

        adapter = ArticleAdapter { mainViewModel.setArticleSelected(it) }
        binding.rvArticles.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvArticles.adapter = adapter

        fetchArticles()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun fetchArticles() {
        Log.d(TAG, "fetchArticles")

        lifecycleScope.launch {
            viewModel.fetchArticles().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
