package com.teamwizdum.wizdum.feature.quest.navigation.argument

import kotlinx.serialization.Serializable

@Serializable
data class LectureArgument(
    val lectureId: Int = 0,
    val orderSeq: Int = 0,
    val lectureTitle: String = "",
    val lectureStatus: String = "",
    val isLastLecture: Boolean = false,
    val mentorName: String = "",
    val mentorImgUrl: String = "",
    val userName: String = "",
)
