package com.example.simplerxapp.api.rao

import com.example.simplerxapp.api.ApiEndpoints
import com.example.simplerxapp.models.ApiEndpointsModel

class ApiEndpointRao(private val baseUrl: String) {

    fun fetch(): ApiEndpoints = ApiEndpointsModel(
        subjects = "$baseUrl/content/api/v1/subjects",
        chapters = "$baseUrl/content/api/v1/subjects/%s/chapters",
        testArgs = "$baseUrl/atest/with/%s/ADIGIT=%d/%s"
    )
}