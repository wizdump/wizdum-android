package com.teamwizdum.wizdum.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class MentorsRequest(
    val questionId: Int,
    val aiRecommendation: Boolean
)