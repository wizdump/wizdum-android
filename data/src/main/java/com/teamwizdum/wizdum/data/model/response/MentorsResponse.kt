package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MentorsResponse(
    val id: Int,
    val name: String,
    val title: String,
    val description: String
)