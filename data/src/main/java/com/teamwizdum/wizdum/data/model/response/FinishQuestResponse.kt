package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class FinishQuestResponse(
    val encouragement: String
)