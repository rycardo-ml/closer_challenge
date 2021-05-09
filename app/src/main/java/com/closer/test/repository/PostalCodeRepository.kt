package com.closer.test.repository

import android.util.Log
import com.closer.test.util.Resource
import com.closer.test.util.database.AppDatabase
import com.closer.test.util.model.PostalCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "PostalCodeRepository"

class PostalCodeRepository(
    val database: AppDatabase
) {

    private val postalCodeDAO = database.postalCodeDAO()

    suspend fun searchPostalCodes(text: String): List<PostalCode> {

        val filter = "*${text.replace('-', ' ')}*"
        Log.d(TAG, "searchPostalCodes $filter")

        return postalCodeDAO.searchFTS(filter)
    }

    fun fetchPostalCode(): Flow<Resource<List<PostalCode>>> {
        Log.d(TAG, "fetchPostalCode")

        return flow {

            emit(Resource.Loading())

            val databaseValues = postalCodeDAO.getAll()
            if (databaseValues.isNullOrEmpty()) {
                emit(Resource.Fetch())
                return@flow
            }

            emit(Resource.Success(databaseValues))
        }
    }
}
