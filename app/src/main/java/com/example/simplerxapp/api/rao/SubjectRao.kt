package com.example.simplerxapp.api.rao


import com.example.simplerxapp.api.RestClient
import com.example.simplerxapp.models.SubjectModel
import com.example.simplerxapp.models.dto.SubjectResponseDto
import com.example.simplerxapp.settings.Settings
import okhttp3.Request

class SubjectRao(
    private val endpointsRao: ApiEndpointRao = ApiEndpointRao(Settings.baseUrl),
    restClient: RestClient = RestClient.instance
): BaseRao(restClient) {

    fun getAllSubjects(): Result<List<SubjectModel>> {
        val request = Request.Builder()
            .get()
            .url(endpointsRao.fetch().getSubjects())
            .build()
        val ret =  super.client.execute(
            request,
            SubjectResponseDto::class.java
        )
        if (ret.isSuccess) {
            return Result.success(ret.getOrNull()?._embedded?.subjects ?: listOf())
        } else {
            return Result.failure(ret.exceptionOrNull() ?: RuntimeException("Failed to fetch subjects"))
        }

//        val request2 = Request.Builder()
//            .get()
//            .url(endpointsRao.fetch().getChaptersForId(ret[2].id))
//            .build()
//        super.client.execute(request2)?.let {
//            Log.d("TAG", "Chapters: ${it.body?.string()}")
//        }
//
//        val request3 = Request.Builder()
//            .get()
//            .url(endpointsRao.fetch().testWithMultipleParams())
//            .build()
//        super.client.execute(request3)?.let {
//            Log.d("TAG", "Test request: ${it.body?.string()}")
//        }
    }
}