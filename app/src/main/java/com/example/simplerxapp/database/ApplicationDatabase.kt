package com.example.simplerxapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simplerxapp.database.dao.ChapterDao
import com.example.simplerxapp.database.dao.SubjectDao
import com.example.simplerxapp.database.entities.ChapterEntity
import com.example.simplerxapp.database.entities.SubjectEntity

@Database(
    entities = [
        SubjectEntity::class,
        ChapterEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class ApplicationDatabase: RoomDatabase() {

    abstract fun getSubjectDao(): SubjectDao
    abstract fun getChapterDao(): ChapterDao

    companion object {
        const val DATABASE_NAME = "app_db"
        const val SUBJECTS_TABLE = "subjects_tb"
        const val CHAPTERS_TABLE = "chapters_tb"

        private var _instance: ApplicationDatabase? = null

        val instance: ApplicationDatabase
            get() = _instance!!

        @Synchronized
        fun create(context: Context) {
            if (_instance == null) {
                _instance = createDefault(context)
            }
        }

        private fun createDefault(context: Context): ApplicationDatabase {
            return Room.databaseBuilder(context, ApplicationDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}