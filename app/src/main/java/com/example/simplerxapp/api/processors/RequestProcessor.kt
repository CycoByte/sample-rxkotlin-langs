package com.example.simplerxapp.api.processors

import okhttp3.Request
import okhttp3.Response

interface RequestProcessor {
    suspend fun process(request: Request): Response
}