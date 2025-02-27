package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MentorsResponse(
    val mentoId: Int,
    val mentoName: String,
    val mentoTitle: String,
    val itemLevel: String,
    val wiseSaying: String,
    val benefits: List<String>,
    val lectures: List<Lecture>
)

@Serializable
data class Lecture(
    val lectureId: Int,
    val orderSeq: Int,
    val title: String
)