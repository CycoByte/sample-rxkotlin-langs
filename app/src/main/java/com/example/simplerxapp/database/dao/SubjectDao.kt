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
import com.example.simplerxapp.database.entities.SubjectEntity

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun storeSubject(subject: SubjectEntity)

    @Update
    fun updateSubject(subject: SubjectEntity)

    @Delete
    fun deleteSubject(subject: SubjectEntity)

    @Query("SELECT * FROM ${ApplicationDatabase.SUBJECTS_TABLE}")
    fun fetchAll(): List<SubjectEntity>

    @Query("SELECT * FROM ${ApplicationDatabase.SUBJECTS_TABLE} WHERE id=:subId")
    fun fetchById(subId: String): SubjectEntity?

    @Transaction
    fun save(subject: SubjectEntity) {
        val existing = fetchById(subject.id)
        if (existing != null) {
            updateSubject(subject)
        } else {
            storeSubject(subject)
        }
    }

    @Transaction
    fun deleteAll() {
        fetchAll().onEach {
            deleteSubject(it)
        }
    }
}