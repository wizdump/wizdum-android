package com.teamwizdum.wizdum.feature.quest.navigation.argument

import kotlinx.serialization.Serializable

@Serializable
data class LectureClearArgument(
    val classId: Int = 0,
    val lectureId: Int = 0,
    val orderSeq: Int = 0,
    val mentorName: String = "",
    val lectureName: String = "",
    val encouragement: String = ""
)
