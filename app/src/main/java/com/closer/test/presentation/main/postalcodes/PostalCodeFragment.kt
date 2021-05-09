package com.closer.test.presentation.main.postalcodes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.closer.test.databinding.PostalCodeFragmentBinding
import com.closer.test.presentation.main.postalcodes.adapter.PostalCodeAdapter
import com.closer.test.util.Resource
import com.closer.test.util.model.PostalCode
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Error

private const val TAG = "PostalCodeFragment"

@AndroidEntryPoint
class PostalCodeFragment : Fragment() {

    companion object {
        fun newInstance() = PostalCodeFragment()
    }

    private var _binding: PostalCodeFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PostalCodeAdapter

    private val viewModel by viewModels<PostalCodeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = PostalCodeFragmentBinding.inflate(inflater, container, false)


        adapter = PostalCodeAdapter()
        binding.rvPostalCodes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPostalCodes.adapter = adapter

        binding.btFetchPostalCodes.setOnClickListener { viewModel.fetchPostalCodes() }

        binding.etSearch.doAfterTextChanged { editable ->
            editable?.toString()?.let {
                viewModel.filterPostalCodes(it)
            }
        }

        registerObservables()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        unregisterObservables()
        _binding = null
    }

    private fun handlePostalCode(resource: Resource<List<PostalCode>>) {
        Log.d(TAG, "handlePostalCode $resource")

        if (resource is Resource.Success) {
            adapter.refreshData(resource.data!!)
            return
        }

        if (resource is Resource.Loading) {

            return
        }

        if (resource is Resource.Fetch) {

            return
        }

        if (resource is Error) {

            return
        }
    }

    private fun registerObservables() {
        viewModel.postalCodes.observe(viewLifecycleOwner, {
            handlePostalCode(it)
        })
    }

    private fun unregisterObservables() {
        viewModel.postalCodes.removeObservers(viewLifecycleOwner)
    }
}
