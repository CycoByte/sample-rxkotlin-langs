package com.example.simplerxapp.observables.subject

import com.example.simplerxapp.database.dao.SubjectDao
import com.example.simplerxapp.database.entities.SubjectEntity
import com.example.simplerxapp.models.SubjectModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataObservable(
    private val subjectDao: SubjectDao
) {

    suspend fun fetchAll(): List<SubjectModel> = subjectDao.fetchAll().map { it.toDomain() }

    suspend fun saveAll(items: List<SubjectModel>) = subjectDao.saveAll(items.map { it.toEntity() })

    suspend fun deleteAll() = subjectDao.deleteAll()

    fun getFetchAllObservable(): Flow<List<SubjectModel>> = subjectDao.fetchAllObservable().map { subs ->
        subs.map { it.toDomain() }
    }

    private fun SubjectEntity.toDomain(): SubjectModel {
        return SubjectModel(
            id = this.id,
            status = this.status,
            title = this.title,
            languageCode = this.languageCode
        )
    }

    private fun SubjectModel.toEntity(): SubjectEntity {
        return SubjectEntity(
            id = this.id,
            status = this.status,
            title = this.title,
            languageCode = this.languageCode
        )
    }
}