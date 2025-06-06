package com.teamwizdum.wizdum.feature.lecture.navigation.argument

import kotlinx.serialization.Serializable

@Serializable
data class LectureArgument(
    val classId: Int = 0,
    val lectureId: Int = 0,
    val orderSeq: Int = 0,
    val lectureTitle: String = "",
    val lectureStatus: String = "",
    val isLastLecture: Boolean = false,
    val mentorName: String = "",
    val mentorImgUrl: String = "",
    val userName: String = "",
)
