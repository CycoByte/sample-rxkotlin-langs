package com.example.simplerxapp.api.rao

import com.example.simplerxapp.api.RestClient
import com.example.simplerxapp.models.ChapterModel
import com.example.simplerxapp.models.dto.ChaptersResponseDto
import com.example.simplerxapp.settings.Settings
import okhttp3.Request

class ChapterRao(
    private val endpointsRao: ApiEndpointRao = ApiEndpointRao(Settings.baseUrl),
    restClient: RestClient = RestClient.instance
): BaseRao(restClient) {

    suspend fun fetchChaptersForId(id: String): Result<List<ChapterModel>> {
        val request = Request.Builder()
            .get()
            .url(endpointsRao.fetch().getChaptersForId(id))
            .build()
        val ret =  super.client.execute(
            request,
            ChaptersResponseDto::class.java
        )
        if (ret.isSuccess) {
            return Result.success(ret.getOrNull()?._embedded?.chapters ?: listOf())
        } else {
            return Result.failure(ret.exceptionOrNull() ?: RuntimeException("Failed to fetch chapter"))
        }
    }
}