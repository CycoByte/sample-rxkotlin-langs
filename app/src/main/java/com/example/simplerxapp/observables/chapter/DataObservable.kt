package com.example.simplerxapp.observables.chapter

import com.example.simplerxapp.database.dao.ChapterDao
import com.example.simplerxapp.database.entities.ChapterEntity
import com.example.simplerxapp.models.ChapterModel
import kotlinx.coroutines.flow.map

class DataObservable(
    private val dao: ChapterDao
) {

    suspend fun fetchForSubjectId(id: String) = dao.fetchForSubjectId(id).map { it.toDomain() }

    suspend fun saveAll(items: List<ChapterModel>) = items.forEach {
        dao.save(it.toEntity())
    }

    suspend fun deleteAll() = dao.deleteAll()

    fun observableForSubjectId(id: String) = dao.fetchObservableForSubjectId(id).map {
        it.map { it.toDomain() }
    }

    companion object {
        private fun ChapterModel.toEntity(): ChapterEntity {
            return ChapterEntity(
                id = this.id,
                status = this.status,
                type = type,
                title = title,
                description = description,
                template = template,
                subjectId = subjectId
            )
        }

        private fun ChapterEntity.toDomain(): ChapterModel {
            return ChapterModel(
                id = this.id,
                status = this.status,
                type = type,
                title = title,
                description = description,
                template = template,
                subjectId = subjectId
            )
        }
    }
}