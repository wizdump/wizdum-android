package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class KeywordResponse(
    val keywordId: Int = 0,
    val value: String,
    val fileUrl: String = "",
    val description: String
)