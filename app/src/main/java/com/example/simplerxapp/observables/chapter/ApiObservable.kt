package com.example.simplerxapp.observables.chapter

import com.example.simplerxapp.api.rao.ChapterRao

class ApiObservable(
    private val apiRao: ChapterRao = ChapterRao() //resolve with DI
) {
    suspend fun fetchForSubjectId(id: String) = apiRao.fetchChaptersForId(id)
}