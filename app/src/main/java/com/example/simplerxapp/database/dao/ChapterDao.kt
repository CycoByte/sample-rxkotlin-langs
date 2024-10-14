package com.example.simplerxapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.simplerxapp.database.ApplicationDatabase
import com.example.simplerxapp.database.entities.ChapterEntity

@Dao
interface ChapterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(chapter: ChapterEntity)

    @Update
    fun update(chapter: ChapterEntity)

    @Delete
    fun delete(chapter: ChapterEntity)

    @Query("SELECT * FROM ${ApplicationDatabase.CHAPTERS_TABLE} WHERE id=:id")
    fun fetchById(id: String): ChapterEntity?

    @Query("SELECT * FROM ${ApplicationDatabase.CHAPTERS_TABLE} WHERE subjectId=:subId")
    fun fetchForSubjectId(subId: String): List<ChapterEntity>

    @Query("DELETE FROM ${ApplicationDatabase.CHAPTERS_TABLE}")
    fun deleteAll()

    @Transaction
    fun save(chapter: ChapterEntity) {
        val retrieved = fetchById(chapter.id)
        if (retrieved != null) {
            update(chapter)
        } else {
            add(chapter)
        }
    }
}