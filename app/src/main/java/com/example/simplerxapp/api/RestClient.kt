package com.example.simplerxapp.api

import com.example.simplerxapp.api.processors.ErrorProcessor
import com.example.simplerxapp.api.processors.RequestLogProcessor
import com.example.simplerxapp.api.processors.RequestProcessor
import com.example.simplerxapp.api.processors.RestProcessor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

class RestClient private constructor() {

    protected lateinit var processor: RequestProcessor

    fun init() {
        val client = OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .writeTimeout(10000, TimeUnit.MILLISECONDS)
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .build()
        processor = RequestLogProcessor(ErrorProcessor(RestProcessor(client)))
    }

    fun <T> execute(request: Request, expected: Class<T>): Result<T> {
        return try {
            val response = processor.process(request).body ?: return Result.failure(NullPointerException("Empty response"))
            val json = response.string()
            Result.success(Gson().fromJson(json, expected))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    fun execute(request: Request): Response = processor.process(request)

    companion object {

        private lateinit var _instance: RestClient

        private fun create(): RestClient {
            return RestClient()
        }

        val instance: RestClient
            get() {
                if (!this::_instance.isInitialized) {
                    _instance = create().also { it.init() }
                }
                return _instance
            }
    }
}