package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class RewardResponse(
    val createdAt: String = "",
    val filePath: String = "",
    val mentoName: String = "",
    val rewardId: Int = 0,
    val summary: String = "",
    val userName: String = ""
)