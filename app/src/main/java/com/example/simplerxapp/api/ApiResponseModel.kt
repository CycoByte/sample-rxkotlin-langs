package com.example.simplerxapp.api

class ApiResponseModel(
    val code: Int,
    val url: String,
    val headers: List<Pair<String, String>>,
    val method: Method,
    val body: String?
) {
    enum class Method {
        GET,
        POST
    }

    companion object {
        fun String.toMethod(): Method {
            return when {
                equals("post", ignoreCase = true) -> Method.POST
                else -> Method.GET
            }
        }
    }
}