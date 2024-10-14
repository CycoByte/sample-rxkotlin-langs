package com.example.simplerxapp.observables.endpoints

import com.example.simplerxapp.api.ApiEndpoints
import com.example.simplerxapp.api.rao.ApiEndpointRao
import io.reactivex.rxjava3.core.Maybe

class EndpointsObservable(
    private val endpointRao: ApiEndpointRao
) {

    fun fetchAll(): Maybe<ApiEndpoints> {
        return Maybe.fromCallable {
            endpointRao.fetch()
        }
    }
}