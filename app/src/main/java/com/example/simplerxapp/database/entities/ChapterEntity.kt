package com.example.simplerxapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simplerxapp.database.ApplicationDatabase

@Entity(tableName = ApplicationDatabase.CHAPTERS_TABLE)
data class ChapterEntity(
    @PrimaryKey val id: String,
    val status: String,
    val type: String,
    val title: String,
    val description: String?,
    val template: Boolean,
    val subjectId: String
)
