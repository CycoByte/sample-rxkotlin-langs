package com.example.simplerxapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.simplerxapp.database.ApplicationDatabase
import com.example.simplerxapp.database.entities.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeSubject(subject: SubjectEntity)

    @Update
    suspend fun updateSubject(subject: SubjectEntity)

    @Delete
    suspend fun deleteSubject(subject: SubjectEntity)

    @Query("SELECT * FROM ${ApplicationDatabase.SUBJECTS_TABLE}")
    suspend fun fetchAll(): List<SubjectEntity>

    @Query("SELECT * FROM ${ApplicationDatabase.SUBJECTS_TABLE}")
    fun fetchAllObservable(): Flow<List<SubjectEntity>>

    @Query("SELECT * FROM ${ApplicationDatabase.SUBJECTS_TABLE} WHERE id=:subId")
    suspend fun fetchById(subId: String): SubjectEntity?

    @Transaction
    suspend fun save(subject: SubjectEntity) {
        val existing = fetchById(subject.id)
        if (existing != null) {
            updateSubject(subject)
        } else {
            storeSubject(subject)
        }
    }

    @Transaction
    suspend fun saveAll(subject: List<SubjectEntity>) {
        subject.forEach { subject ->
            val existing = fetchById(subject.id)
            if (existing != null) {
                updateSubject(subject)
            } else {
                storeSubject(subject)
            }
        }
    }

    @Transaction
    suspend fun deleteAll() {
        fetchAll().onEach {
            deleteSubject(it)
        }
    }
}