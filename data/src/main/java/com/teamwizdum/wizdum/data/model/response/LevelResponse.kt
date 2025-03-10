package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LevelResponse(
    val levelId: Int = 0,
    val level: String,
    val value: String = "",
    val description: String,
)