package com.closer.test.repository

import android.util.Log
import androidx.room.withTransaction
import com.closer.test.util.Resource
import com.closer.test.util.database.AppDatabase
import com.closer.test.util.model.PostalCode
import com.closer.test.util.network.PostalCodeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.regex.Pattern


private const val TAG = "PostalCodeRepository"

class PostalCodeRepository(
    val postalCodeAPI: PostalCodeService,
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
            if (!databaseValues.isEmpty()) {
                Log.d(TAG, "fetchPostalCode return database values")
                emit(Resource.Success(databaseValues))
                return@flow
            }


            val responseBody = postalCodeAPI.downloadPostalCodeFile()

            Log.d(TAG, "fetchPostalCode before read data")

            val stream: InputStream = responseBody.body()!!.byteStream()

            val pattern = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")
            val postalCodes = createLines(stream).map {
                convertLineToPostalCode(it, pattern)
            }

            Log.d(TAG, "fetchPostalCode before save")

            database.withTransaction {
                postalCodeDAO.deleteAll()
                postalCodeDAO.insert(*postalCodes.toTypedArray())
            }

            Log.d(TAG, "fetchPostalCode after save")

            emit(Resource.Success(postalCodes))
        }
    }

    private fun convertLineToPostalCode(line: String, pattern: Pattern): PostalCode {
        val splitData = pattern.split(line)

        return PostalCode(
            splitData[0].toLong(),
            splitData[1].toLong(),
            splitData[2].toLong(),
            splitData[3],

            splitData[4].toLongOrNull(),
            splitData[5],
            splitData[6],
            splitData[7],
            splitData[8],
            splitData[9],
            splitData[10],
            splitData[11],
            splitData[12],
            splitData[13],

            splitData[14].toLong(),
            splitData[15].toLong(),
            splitData[16]
            )
    }

    private fun createLines(stream: InputStream): List<String> {

        val br = BufferedReader(InputStreamReader(stream))

        var line: String?
        var processLine = false
        val list = mutableListOf<String>()
        while (br.readLine().also { line = it } != null) {
            if (!processLine) {
                processLine = true
                continue
            }

            list.add(line!!)
        }

        return list
    }
}
