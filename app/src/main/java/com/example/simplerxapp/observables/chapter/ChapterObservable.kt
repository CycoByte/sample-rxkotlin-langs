package com.example.simplerxapp.observables.chapter

import com.example.simplerxapp.database.ApplicationDatabase
import com.example.simplerxapp.models.ChapterModel
import kotlinx.coroutines.flow.Flow

class ChapterObservable(
    private val apiObservable: ApiObservable = ApiObservable(),
    private val dataObservable: DataObservable = DataObservable(ApplicationDatabase.instance.getChapterDao())
) {

    private var memory: List<ChapterModel> = listOf()

    suspend fun fetchAllChaptersLocal(subjectId: String): List<ChapterModel> = dataObservable.fetchForSubjectId(subjectId)
    suspend fun fetchAllChaptersRemote(subjectId: String): Result<List<ChapterModel>> = apiObservable.fetchForSubjectId(subjectId)
    suspend fun deleteAllLocal() = dataObservable.deleteAll()
    fun observableLocalChapters(id: String): Flow<List<ChapterModel>> = dataObservable.observableForSubjectId(id)
    suspend fun updateLocalFromRemote(subjectId: String): Result<Unit> {
        val res = fetchAllChaptersRemote(subjectId)
        return if (res.isSuccess) {
            res.getOrNull()?.let {
                memory = it
                dataObservable.saveAll(it)
            }
            Result.success(Unit)
        } else {
            Result.failure(res.exceptionOrNull() ?: RuntimeException("Failed to update entries"))
        }
    }
}