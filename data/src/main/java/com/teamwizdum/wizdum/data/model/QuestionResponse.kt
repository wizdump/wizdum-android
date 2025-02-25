package com.teamwizdum.wizdum.data.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionResponse(
    val content: String,
    val level: String,
    val questionId: Int
)