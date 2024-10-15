package com.example.simplerxapp.api.processors

import android.util.Log
import okhttp3.Request
import okhttp3.Response

class RequestLogProcessor(private val next: RequestProcessor): RequestProcessor {
    override suspend fun process(request: Request): Response {
        Log.d("NETWORK", "request -> ${request.url}")
        val response = next.process(request)
        val execTime = response.receivedResponseAtMillis - response.sentRequestAtMillis
        Log.d("NETWORK", "response <- $response  (${execTime} ms)")
//        Log.d("NETWORK", "response -> ${response.body?.string()}")
        return response
    }
}