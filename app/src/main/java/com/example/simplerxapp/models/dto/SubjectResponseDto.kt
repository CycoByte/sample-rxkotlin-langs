package com.example.simplerxapp.models.dto

import com.example.simplerxapp.models.SubjectModel

data class SubjectResponseDto(
    override val _embedded: Embedded
): ApiBaseResponse<SubjectResponseDto.Embedded> {
    data class Embedded(
        val subjects: List<SubjectModel>
    )
}