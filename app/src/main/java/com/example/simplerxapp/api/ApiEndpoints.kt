package com.example.simplerxapp.api

interface ApiEndpoints {
    fun getSubjects(): String
    fun getChaptersForId(id: String): String
    fun testWithMultipleParams(): String
}