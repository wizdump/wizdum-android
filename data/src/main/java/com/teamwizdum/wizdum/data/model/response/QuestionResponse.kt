package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class QuestionResponse(
    val questionId: Int = 0,
    val content: String,
    val level: String,
)