package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    val username: String,
    val myWizCount: Int,
    val friendWithLectureCount: Int,
    val beforeAndInProgressLectures: List<BeforeAndInProgressLecture> = emptyList(),
    val finishLectures: List<FinishLecture> = emptyList()
)

@Serializable
data class BeforeAndInProgressLecture(
    val mentoId: Int,
    val mentoName: String,
    val logoFilePath: String,
    val classId: Int,
    val lectureId: Int,
    val classTitle: String,
    val lectureStatus: String,
    val itemLevel: String,
)

@Serializable
data class FinishLecture(
    val mentoId: Int,
    val mentoName: String,
    val classId: Int,
    val lectureId: Int,
    val classTitle: String,
    val completedAt: String,
)