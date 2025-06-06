package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MentorDetailResponse(
    val mentoId: Int = 0,
    val mentoName: String = "",
    val classTitle: String = "",
    val backgroundImageFilePath: String = "",
    val mentoringStyle: String = "",
    val itemLevel: String = "",
    val wiseSaying: String= "",
    val friendWithLectureCount: Int = 0,
    val benefits: List<String> = emptyList(),
    val lectures: List<Lecture> = emptyList()
)

@Serializable
data class Lecture(
    val lectureId: Int = 0,
    val orderSeq: Int,
    val title: String,
    val lectureLogoFilePath: String = "",
)