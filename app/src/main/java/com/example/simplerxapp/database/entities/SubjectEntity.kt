package com.example.simplerxapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simplerxapp.database.ApplicationDatabase

@Entity(tableName = ApplicationDatabase.SUBJECTS_TABLE)
data class SubjectEntity(
    @PrimaryKey val id: String,
    val languageCode: String,
    val status: String,
    val title: String
)
