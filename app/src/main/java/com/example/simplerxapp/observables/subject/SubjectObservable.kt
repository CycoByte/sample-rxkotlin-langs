package com.example.simplerxapp.observables.subject

import com.example.simplerxapp.database.ApplicationDatabase
import com.example.simplerxapp.models.SubjectModel
import kotlinx.coroutines.flow.Flow

class SubjectObservable(
    private val apiObservable: ApiObservable = ApiObservable(),
    private val dataObservable: DataObservable = DataObservable(ApplicationDatabase.instance.getSubjectDao())
) {

    suspend fun fetchAllLocal(): List<SubjectModel> = dataObservable.fetchAll()
    suspend fun deleteAllLocal() = dataObservable.deleteAll()
    suspend fun fetchAllRemote(): Result<List<SubjectModel>> = apiObservable.fetchAll()

    fun fetchAllLocalObservable(): Flow<List<SubjectModel>> = dataObservable.getFetchAllObservable()

    suspend fun updateLocalFromRemote(): Result<List<SubjectModel>?> {
        val res = apiObservable.fetchAll()
        return if (res.isSuccess) {
            res.getOrNull()?.let {
                dataObservable.saveAll(it)
            }
            Result.success(res.getOrNull())
        } else {
            Result.failure(res.exceptionOrNull() ?: RuntimeException("Update error"))
        }
    }
}