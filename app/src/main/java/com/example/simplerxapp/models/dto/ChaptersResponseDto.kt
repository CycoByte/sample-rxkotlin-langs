package com.example.simplerxapp.models.dto

import com.example.simplerxapp.models.ChapterModel
import com.example.simplerxapp.models.SubjectModel

/**
"title":"chapter 2",
"description":null,
"iconUrl":null,
"index":2,
"subjectId":2055,
"status":"PUBLISHED",
"section":"LEARNING_PATH",
"allowedMethods":[

],
"updatedAt":"2023-09-06 07:27:01",
"contentLastUpdatedAt":null,
"_links":{
"self":{
"href":"https://business-school-development.development.languages.akelius.com/content/api/v1/chapters/20757"
},
"test":{
"href":"https://business-school-development.development.languages.akelius.com/content/api/v1/chapters/20757/test"
},
"contentUnits":{
"href":"https://business-school-development.development.languages.akelius.com/content/api/v1/chapters/20757/contentunits{?method}",
"templated":true
}
},
"id":20757,
"type":"Chapter"
},
{
"title":"chapter 1",
"description":null,
"iconUrl":null,
"index":1,
"subjectId":2055,
"status":"PUBLISHED",
"section":"LEARNING_PATH",
"allowedMethods":[

],
"updatedAt":"2023-09-06 07:26:20",
"contentLastUpdatedAt":null,
"_links":{
"self":{
"href":"https://business-school-development.development.languages.akelius.com/content/api/v1/chapters/20756"
},
"test":{
"href":"https://business-school-development.development.languages.akelius.com/content/api/v1/chapters/20756/test"
},
"contentUnits":{
"href":"https://business-school-development.development.languages.akelius.com/content/api/v1/chapters/20756/contentunits{?method}",
"templated":true
}
},
"id":20756,
"type":"Chapter"

 * */

data class ChaptersResponseDto(
    override val _embedded: Embedded
): ApiBaseResponse<ChaptersResponseDto.Embedded> {
    data class Embedded(
        val chapters: List<ChapterModel>
    )
}