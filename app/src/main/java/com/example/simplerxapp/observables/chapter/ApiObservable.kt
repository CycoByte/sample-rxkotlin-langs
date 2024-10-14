package com.example.simplerxapp.observables.chapter

import com.example.simplerxapp.api.rao.ChapterRao
import com.example.simplerxapp.models.ChapterModel
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

class ApiObservable(
    private val apiRao: ChapterRao = ChapterRao() //resolve with DI
) {

    fun fetchForSubjectId(id: String): Maybe<List<ChapterModel>> {
        return Maybe.fromCallable {
            val result = apiRao.fetchChaptersForId(id)
            if (result.isSuccess) {
                result.getOrNull()
            } else {
                throw result.exceptionOrNull() ?: RuntimeException("Failed to retrieve subjects")
            }
        }.subscribeOn(Schedulers.io())
    }
}