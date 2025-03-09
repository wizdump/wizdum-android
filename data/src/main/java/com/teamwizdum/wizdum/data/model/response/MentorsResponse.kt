package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MentorsResponse(
    val classId: Int = 0,
    val mentoId: Int = 0,
    val mentoName: String,
    val classTitle: String,
    val logoImageFilePath: String? = "",
    val itemLevel: String
)