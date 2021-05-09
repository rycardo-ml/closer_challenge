package com.closer.test.presentation.main.postalcodes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closer.test.repository.PostalCodeRepository
import com.closer.test.util.Resource
import com.closer.test.util.model.PostalCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "PostalCodeViewModel"

@HiltViewModel
class PostalCodeViewModel @Inject constructor(
    private val postalCodeRepository: PostalCodeRepository,
) : ViewModel() {

    private val _postalCodes = MutableLiveData<Resource<List<PostalCode>>>()
    val postalCodes: LiveData<Resource<List<PostalCode>>> get() = _postalCodes

    fun filterPostalCodes(text: String) {
        Log.d(TAG, "filterPostalCodes $text")

        viewModelScope.launch {
            val list = postalCodeRepository.searchPostalCodes(text)
            _postalCodes.postValue(Resource.Success(list))
        }
    }

    fun fetchPostalCodes() {
        viewModelScope.launch {
            postalCodeRepository.fetchPostalCode().collect {
                Log.d(TAG, "fetchPostalCodes ${it}")
                _postalCodes.value = it
            }
        }
    }
}