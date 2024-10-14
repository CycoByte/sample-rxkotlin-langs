package com.example.simplerxapp.observables.subject

import com.example.simplerxapp.api.rao.SubjectRao
import com.example.simplerxapp.models.SubjectModel
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

class ApiObservable(
    private val apiRao: SubjectRao = SubjectRao() //resolve with DI
) {

    fun fetchAll(): Maybe<List<SubjectModel>> {
        return Maybe.fromCallable {
            val result = apiRao.getAllSubjects()
            if (result.isSuccess) {
                result.getOrNull()
            } else {
                throw result.exceptionOrNull() ?: RuntimeException("Failed to retrieve subjects")
            }
        }.subscribeOn(Schedulers.io())
    }
}