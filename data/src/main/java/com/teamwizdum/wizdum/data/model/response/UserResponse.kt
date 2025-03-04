package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val userId: Int = 1,
    val snsId: Long = 0L,
    val email: String = "",
    val name: String = "",
    val password: String = "",
)