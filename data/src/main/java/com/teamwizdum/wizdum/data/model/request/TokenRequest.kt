package com.teamwizdum.wizdum.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    val accessToken: String
)