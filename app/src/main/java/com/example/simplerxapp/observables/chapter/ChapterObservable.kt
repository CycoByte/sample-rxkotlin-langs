package com.example.simplerxapp.observables.chapter

import com.example.simplerxapp.database.ApplicationDatabase
import com.example.simplerxapp.models.ChapterModel
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

class ChapterObservable(
    private val apiObservable: ApiObservable = ApiObservable(),
    private val dataObservable: DataObservable = DataObservable(ApplicationDatabase.instance.getChapterDao())
) {

    private var memory: List<ChapterModel> = listOf()

    fun fetchAllChaptersLocal(subjectId: String): Maybe<List<ChapterModel>> = dataObservable.fetchForSubjectId(subjectId)
    fun fetchAllChaptersRemote(subjectId: String): Maybe<List<ChapterModel>> = apiObservable.fetchForSubjectId(subjectId)
    fun deleteAllLocal() = dataObservable.deleteAll()

    fun fetchForSubject(id: String): Maybe<List<ChapterModel>> {
        if (memory.isNotEmpty()) {
            return Maybe.just(memory)
        }
        return apiObservable
            .fetchForSubjectId(id)
            .doOnSuccess {
                memory = it
                dataObservable.saveAll(it)
                    .observeOn(Schedulers.io())
                    .subscribe()
            }
            .flatMap {
                dataObservable.fetchForSubjectId(id)
            }
    }
}