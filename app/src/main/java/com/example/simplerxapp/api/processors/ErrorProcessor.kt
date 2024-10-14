package com.example.simplerxapp.api.processors

import android.util.Log
import okhttp3.Request
import okhttp3.Response

class ErrorProcessor(private val next: RequestProcessor): RequestProcessor {
    override fun process(request: Request): Response {
        val response = next.process(request)
        when (response.code) {
            in 200..299 -> {} // request is successfull
            403,
            401 -> {
                Log.e("REQUEST ERROR", "Unauthorized access")
            }
            in 400..499 -> {
                Log.e("REQUEST ERROR", "Connection error")
            }
            in 500..599 -> {
                Log.e("REQUEST ERROR", "Server error")
            }
            else -> {
                Log.e("REQUEST ERROR", "Unknown error code ${response.code}")
            }
        }
        return response
    }
}