package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    val myWizCount: Int,
    val friendWithLectureCount: Int,
    val beforeAndInProgressLectures: List<BeforeAndInProgressLecture> = emptyList(),
    val finishLectures: List<FinishLecture> = emptyList()
)

@Serializable
data class BeforeAndInProgressLecture(
    val mentoId: Int,
    val mentoName: String,
    val lectureId: Int,
    val lectureStatus: String,
    val itemLevel: String,
)

@Serializable
data class FinishLecture(
    val mentoId: Int,
    val mentoName: String,
    val lectureId: Int,
    val completedAt: String,
)