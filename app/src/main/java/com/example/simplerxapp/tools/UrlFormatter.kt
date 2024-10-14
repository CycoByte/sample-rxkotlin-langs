package com.example.simplerxapp.tools

import java.net.URLEncoder


object UrlFormatter {
    fun String.formatArgs(vararg params: Any): String {
        val formatted = params.map {
            if (it is String) {
                URLEncoder.encode(it, "utf-8")
            } else {
                it
            }
        }.toTypedArray()
        return try {
            this.format(*formatted)
        } catch (e: Exception) {
            e.printStackTrace()
            this
        }
    }
}