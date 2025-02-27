package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MentorsResponse(
    val mentoId: Int = 0,
    val mentoName: String,
    val mentoTitle: String,
    val filePath: String? = "",
    val itemLevel: String
)