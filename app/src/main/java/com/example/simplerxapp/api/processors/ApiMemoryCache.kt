package com.example.simplerxapp.api.processors

import android.util.Log
import com.example.simplerxapp.api.processors.RestProcessor.Companion.cloneResponse
import okhttp3.Request
import okhttp3.Response
import java.security.MessageDigest

class ApiMemoryCache(private val next: RequestProcessor): RequestProcessor {

    private val cache: HashMap<String, Response> = hashMapOf()

    fun clearCached() = cache.clear()

    override suspend fun process(request: Request): Response {
        val sign = getRequestSignature(request)
        if (!cache.containsKey(sign)) {
            Log.d("ApiMemoryCache", "Not in cache - calling api...")
            putInCacheAndReturn(sign, next.process(request))
        } else {
            Log.d("ApiMemoryCache", "Found in cache - retrieving...")
        }
        return cache[sign]!!.cloneResponse()
    }

    private fun getRequestSignature(request: Request): String {
        val httpsFlag = request.url.isHttps
        val url = request.url.toUrl().toString()
        val method = request.method
        val headers = request.headers.map { it.second }.joinToString(postfix = "|") { it }
        return "$httpsFlag|$method|$headers|$url".md5()
    }

    private fun putInCacheAndReturn(key: String, response: Response): Response {
        cache[key] = response.cloneResponse()
        return response
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(this.toByteArray())
        return digest.toHexString()
    }
}