package com.example.simplerxapp.observables.subject

import com.example.simplerxapp.api.rao.SubjectRao
import com.example.simplerxapp.models.SubjectModel

class ApiObservable(
    private val apiRao: SubjectRao = SubjectRao() //resolve with DI
) {
    suspend fun fetchAll(): Result<List<SubjectModel>> = apiRao.getAllSubjects()
}