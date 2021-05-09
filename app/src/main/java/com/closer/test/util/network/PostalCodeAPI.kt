package com.closer.test.util.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

//https://raw.githubusercontent.com/centraldedados/codigos_postais/master/data/codigos_postais.csv
interface PostalCodeAPI {

    @GET("master/data/codigos_postais.csv")
    suspend fun downloadPostalCodeFile(): Response<ResponseBody>
}
