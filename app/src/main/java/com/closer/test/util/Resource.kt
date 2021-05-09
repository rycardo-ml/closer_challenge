package com.closer.test.util

import com.closer.test.util.error.AppError

sealed class Resource<T>(
    val data: T? = null,
    val error: AppError? = null,
    val fetchedFromApi: Boolean = false
){

    class Success<T>(data:T, fetchedFromApi: Boolean = false): Resource<T>(data, null, fetchedFromApi)
    class Loading<T>(data:T? = null): Resource<T>(data)
    class Error<T>(error: AppError, data: T? = null): Resource<T>(data, error)
    class Fetch<T>: Resource<T>()
}