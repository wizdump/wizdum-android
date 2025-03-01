package com.teamwizdum.wizdum.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val type: String = "",
    val lectureId: Int = 0,
    val name: String = "",
    val accessToken: String = "",
    val message: MessageContent,
    val isHide: Boolean? = true,
    val timestamp: String = "",

)

@Serializable
data class MessageContent(
    var content: String? = null,
    val isFinish: Boolean = false,
    val isLast: Boolean = false
)