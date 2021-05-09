package com.closer.test.presentation.main.postalcodes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.closer.test.databinding.PostalCodeFragmentBinding
import com.closer.test.presentation.main.postalcodes.adapter.PostalCodeAdapter
import com.closer.test.presentation.main.postalcodes.service.PostalCodeService
import com.closer.test.util.Resource
import com.closer.test.util.model.PostalCode
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "PostalCodeFragment"

@AndroidEntryPoint
class PostalCodeFragment : Fragment() {

    companion object {
        fun newInstance() = PostalCodeFragment()
    }

    private var _binding: PostalCodeFragmentBinding? = null
    private val binding get() = _binding!!

    private val postalCodeBroadcast = PostalCodeLocalBroadcast()

    private lateinit var adapter: PostalCodeAdapter

    private val viewModel by viewModels<PostalCodeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = PostalCodeFragmentBinding.inflate(inflater, container, false)

        adapter = PostalCodeAdapter()
        binding.rvPostalCodes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPostalCodes.adapter = adapter

        binding.etSearch.doAfterTextChanged { editable ->
            editable?.toString()?.let {
                viewModel.filterPostalCodes(it)
            }
        }

        registerBroadcasts()
        registerObservables()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        unregisterBroadcasts()
        unregisterObservables()
        _binding = null
    }

    private fun handlePostalCode(resource: Resource<List<PostalCode>>) {
        Log.d(TAG, "handlePostalCode $resource")

        if (resource is Resource.Success) {
            adapter.refreshData(resource.data!!)

            binding.layoutFetching.hide()
            binding.layoutLoading.hide()
            binding.layoutError.hide()
            return
        }

        if (resource is Resource.Loading) {
            binding.layoutLoading.show()
            binding.layoutError.hide()
            binding.layoutFetching.hide()

            return
        }

        if (resource is Resource.Fetch) {
            val myServiceIntent = Intent(requireContext(), PostalCodeService::class.java)
            ContextCompat.startForegroundService(requireContext(), myServiceIntent)

//            requireActivity().bindService(
//                Intent(
//                    requireContext(),
//                    PostalCodeService::class.java
//                ), mServiceConnection, BIND_AUTO_CREATE
//            )

            binding.layoutFetching.show()
            binding.layoutLoading.hide()
            binding.layoutError.hide()

//            val serviceIntent = Intent(this, MyForegroundService::class.java)
//            stopService(serviceIntent)

            return
        }

        if (resource is Error) {
            binding.layoutError.show(resource.error)
            binding.layoutLoading.hide()
            binding.layoutFetching.hide()
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

    private fun registerBroadcasts() {
        postalCodeBroadcast.run {
            LocalBroadcastManager
                .getInstance(requireContext())
                .registerReceiver(this@run, IntentFilter("POSTAL_CODE_RECEIVER"))
        }
    }

    private fun unregisterBroadcasts() {
        postalCodeBroadcast.run {
            LocalBroadcastManager
                .getInstance(requireContext())
                .unregisterReceiver(this@run)
        }
    }

    inner class PostalCodeLocalBroadcast: BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            intent?.run {
                val type = this.getStringExtra("STATUS")

                if (type == "OK") {
                    viewModel.fetchPostalCodes()
                    return@run
                }

                if (type == "ERROR") {
                    //FIXME Enviar erro para a tela
                }
            }
        }
    }
}
