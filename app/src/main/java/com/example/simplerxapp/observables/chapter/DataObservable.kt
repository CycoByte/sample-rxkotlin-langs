package com.example.simplerxapp.observables.chapter

import com.example.simplerxapp.database.dao.ChapterDao
import com.example.simplerxapp.database.entities.ChapterEntity
import com.example.simplerxapp.models.ChapterModel
import io.reactivex.rxjava3.core.Maybe

class DataObservable(
    private val dao: ChapterDao
) {

    fun fetchForSubjectId(id: String): Maybe<List<ChapterModel>> {
        return Maybe.fromCallable {
            dao.fetchForSubjectId(id).map { it.toDomain() }
        }
    }

    fun saveAll(items: List<ChapterModel>): Maybe<List<ChapterModel>> {
        return Maybe.fromCallable {
            items.forEach {
                dao.save(it.toEntity())
            }
            items
        }
    }

    fun deleteAll(): Maybe<Unit> {
        return Maybe.fromCallable {
            dao.deleteAll()
        }
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