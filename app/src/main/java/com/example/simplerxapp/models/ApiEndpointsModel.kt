package com.example.simplerxapp.models

import com.example.simplerxapp.api.ApiEndpoints
import com.example.simplerxapp.tools.UrlFormatter.formatArgs

class ApiEndpointsModel(
    private val subjects: String,
    private val chapters: String,
    private val testArgs: String,
) : ApiEndpoints {
    override fun getSubjects(): String = subjects
    override fun getChaptersForId(id: String): String {
        return chapters.formatArgs(id)
    }

    override fun testWithMultipleParams(): String {
        return testArgs.formatArgs("ASTRING", 1590, "ANDANOTHERSTRING")
    }
}