package com.closer.test.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.closer.test.R
import com.closer.test.databinding.MainActivityBinding
import com.closer.test.presentation.articles.ArticlesFragment
import com.closer.test.presentation.main.MainViewModel
import com.closer.test.presentation.postalcodes.PostalCodeFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: MainActivityBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "viewModel $viewModel")

        _binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerObservers()

        binding.navigationView.setOnNavigationItemSelectedListener { handleMenuClicked(it.itemId) }

        if (savedInstanceState == null) {
            binding.navigationView.menu.performIdentifierAction(R.id.navigation_postalCode, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterObservers()
        _binding = null
    }

    private fun handleMenuClicked(id: Int): Boolean {
        when(id) {
            R.id.navigation_postalCode -> {
                openFragment(PostalCodeFragment.newInstance())
                return true
            }

            R.id.navigation_articles -> {
                openFragment(ArticlesFragment.newInstance())
                return true
            }
        }

        return false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private fun registerObservers() {
        viewModel.ldArticle.observe(this, {
            Log.d(TAG, "article selected $it" )
        })
    }

    private fun unregisterObservers() {
        viewModel.ldArticle.removeObservers(this)
    }
}