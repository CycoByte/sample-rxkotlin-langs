package com.example.simplerxapp.observables.subject

import com.example.simplerxapp.database.dao.SubjectDao
import com.example.simplerxapp.database.entities.SubjectEntity
import com.example.simplerxapp.models.SubjectModel
import io.reactivex.rxjava3.core.Maybe

class DataObservable(
    private val subjectDao: SubjectDao
) {

    fun fetchAll(): Maybe<List<SubjectModel>> {
        return Maybe.fromCallable {
            subjectDao.fetchAll().map { it.toDomain() }
        }
    }

    fun saveAll(items: List<SubjectModel>): Maybe<List<SubjectModel>> {
        return Maybe.fromCallable {
            items.forEach {
                subjectDao.save(it.toEntity())
            }
            items
        }
    }

    fun deleteAll(): Maybe<Unit>  {
        return Maybe.fromCallable {
            subjectDao.deleteAll()
        }
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