package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val userId: Int = 0,
    val snsId: Long = 0L,
    val profileImageUrl: String = "",
    val email: String = "",
    val name: String = "",
    val password: String = "",
)