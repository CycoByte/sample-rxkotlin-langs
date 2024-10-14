package com.example.simplerxapp.observables.subject

import com.example.simplerxapp.database.ApplicationDatabase
import com.example.simplerxapp.models.SubjectModel
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers


class SubjectObservable(
//    private val endpointsObservable: EndpointsObservable,
    private val apiObservable: ApiObservable = ApiObservable(),
    private val dataObservable: DataObservable = DataObservable(ApplicationDatabase.instance.getSubjectDao())
) {
    private var inMemory: List<SubjectModel> = listOf()

    fun fetchAllLocal(): Maybe<List<SubjectModel>> = dataObservable.fetchAll()
    fun fetchAllRemote(): Maybe<List<SubjectModel>> = apiObservable.fetchAll()
    fun deleteAllLocal() = dataObservable.deleteAll()

    fun fetchAll(forced: Boolean = false): Maybe<List<SubjectModel>> {
        if (forced) {
            inMemory = listOf()
        }
        return if (inMemory.isNotEmpty()) Maybe.just(inMemory) else apiObservable
            .fetchAll()
            .doOnSuccess {
                inMemory = it
                dataObservable.saveAll(it)
                    .observeOn(Schedulers.io())
                    .subscribe()
            }
            .flatMap {
                dataObservable.fetchAll()
            }
    }
}