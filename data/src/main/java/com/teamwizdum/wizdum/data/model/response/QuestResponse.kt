package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class QuestResponse(
    val canGetWiz: Boolean = false,
    val filePath: String? = "",
    val isFinished: Boolean = false,
    val lectures: List<LectureDetail> = emptyList(),
    val level: String = "HIGH",
    val mentoId: Int = 1,
    val mentoName: String = "스파르타",
    val userName: String = "유니"
)

@Serializable
data class LectureDetail(
    val courseTime: Int,
    val isFinished: Boolean,
    val isInProgress: Boolean,
    val lectureId: Int,
    val lectureStatus: String,
    val orderSeq: Int,
    val preview: String,
    val title: String
)