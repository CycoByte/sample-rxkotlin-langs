package com.example.simplerxapp.api.processors

import android.util.Log
import com.example.simplerxapp.api.ApiResponseModel
import com.example.simplerxapp.api.ApiResponseModel.Companion.toMethod
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.nio.charset.Charset

class RestProcessor(private val client: OkHttpClient): RequestProcessor {
    override suspend fun process(request: Request): Response {
        return try {
            return client.newCall(request).execute()
//            val response = ApiResponseModel(
//                method = apiRes.request.method.toMethod(),
//                code = apiRes.code,
//                url = apiRes.request.url.toString(),
//                headers = request.headers.toList(),
//                body = apiRes.body?.string()
//            )
//            Log.d("TAG", "Parsed responce: $response")
//            apiRes
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    companion object {
        fun Response.cloneResponse(): Response {
            val source = this.body?.source() ?: return this
            source.request(Long.MAX_VALUE)
            val responseBody = source.buffer.clone().readString(Charset.forName("UTF-8"))
            val rebuilt = responseBody.toResponseBody("application/json; charset=utf-8".toMediaType())
            // need to clone since buffer is read only once
            //todo discuss efficiency, option 2 is to read response in ApiResponseModel and use that instead
            return Response.Builder()
                .request(this.request)
                .protocol(this.protocol)
                .message(this.message)
                .code(this.code)
                .body(rebuilt)
                .build()
        }
    }
}