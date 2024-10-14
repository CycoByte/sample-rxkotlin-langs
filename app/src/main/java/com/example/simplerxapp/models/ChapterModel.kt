package com.example.simplerxapp.models

data class ChapterModel(
    val id: String,
    val status: String,
    val type: String,
    val title: String,
    val description: String?,
    val template: Boolean,
    val subjectId: String
)