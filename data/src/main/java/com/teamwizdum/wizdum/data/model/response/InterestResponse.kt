package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class InterestResponse(
    val interestId: Int,
    val value: String,
    val description: String,
    val fileUrl: String
)