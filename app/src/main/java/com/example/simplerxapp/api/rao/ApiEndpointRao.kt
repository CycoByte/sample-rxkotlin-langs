package com.example.simplerxapp.api.rao

import com.example.simplerxapp.api.ApiEndpoints
import com.example.simplerxapp.models.ApiEndpointsModel
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class ApiEndpointRao(
    private val baseUrl: String,
    private val fromRemote: Boolean = false,
) {

    suspend fun fetch(): ApiEndpoints {
        if (fromRemote) {
            fetchRemote()
        }
        return ApiEndpointsModel(
            subjects = "$baseUrl/content/api/v1/subjects",
            chapters = "$baseUrl/content/api/v1/subjects/%s/chapters",
            testArgs = "$baseUrl/atest/with/%s/ADIGIT=%d/%s"
        )
    }

    private suspend fun fetchRemote() {
        val client = OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .writeTimeout(10000, TimeUnit.MILLISECONDS)
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .build()
        val request = Request.Builder()
            .get()
            .url("$baseUrl/endpoints.sample")
            .build()
        client.newCall(request).execute()
        //Parse and build model?
    }
}