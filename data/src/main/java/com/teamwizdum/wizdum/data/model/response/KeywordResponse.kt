package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class KeywordResponse(
    val keywordId: Int,
    val value: String
)