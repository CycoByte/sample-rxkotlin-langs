package com.example.simplerxapp.api.processors

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class RestProcessor(private val client: OkHttpClient): RequestProcessor {
    override fun process(request: Request): Response {
        return try {
            client.newCall(request).execute()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}