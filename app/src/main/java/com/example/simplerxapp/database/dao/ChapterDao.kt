package com.example.simplerxapp.database.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.simplerxapp.database.ApplicationDatabase
import com.example.simplerxapp.database.entities.ChapterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(chapter: ChapterEntity)

    @Update
    suspend fun update(chapter: ChapterEntity)

    @Delete
    suspend fun delete(chapter: ChapterEntity)

    @Query("SELECT * FROM ${ApplicationDatabase.CHAPTERS_TABLE} WHERE id=:id")
    suspend fun fetchById(id: String): ChapterEntity?

    @Query("SELECT * FROM ${ApplicationDatabase.CHAPTERS_TABLE} WHERE subjectId=:subId")
    suspend fun fetchForSubjectId(subId: String): List<ChapterEntity>

    @Query("SELECT * FROM ${ApplicationDatabase.CHAPTERS_TABLE} WHERE subjectId=:subId")
    fun fetchObservableForSubjectId(subId: String): Flow<List<ChapterEntity>>

    @Query("DELETE FROM ${ApplicationDatabase.CHAPTERS_TABLE}")
    suspend fun deleteAll()

    @Transaction
    suspend fun save(chapter: ChapterEntity) {
        val retrieved = fetchById(chapter.id)
        if (retrieved != null) {
            update(chapter)
        } else {
            add(chapter)
        }
    }

    @Transaction
    suspend fun saveIfNeeded(chapter: ChapterEntity) {
        val retrieved = fetchById(chapter.id)
        if (retrieved != null) {
            if (retrieved != chapter) {
                update(chapter)
            }
        } else {
            add(chapter)
        }
    }
}