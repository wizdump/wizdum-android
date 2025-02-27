package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MentorDetailResponse(
    val mentoId: Int = 0,
    val mentoName: String,
    val mentoTitle: String,
    val filePath: String? = "",
    val itemLevel: String,
    val wiseSaying: String= "",
    val benefits: List<String> = emptyList(),
    val lectures: List<Lecture> = emptyList()
)

@Serializable
data class Lecture(
    val lectureId: Int,
    val orderSeq: Int,
    val title: String
)