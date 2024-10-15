package com.example.simplerxapp.observables.endpoints

import com.example.simplerxapp.api.ApiEndpoints
import com.example.simplerxapp.api.rao.ApiEndpointRao
import io.reactivex.rxjava3.core.Maybe

class EndpointsObservable(
    private val endpointRao: ApiEndpointRao
) {
    //could also add endpoint dao to retrieve from DB if needed
    suspend fun fetchAll(): ApiEndpoints {
        return endpointRao.fetch()
    }
}