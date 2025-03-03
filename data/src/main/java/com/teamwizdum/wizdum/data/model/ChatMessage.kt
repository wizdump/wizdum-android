package com.teamwizdum.wizdum.data.model

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ChatMessage(
    val type: String = "",
    val lectureId: Int = 0,
    val name: String = "",
    val accessToken: String = "",
    val message: MessageContent,
    val isHide: Boolean? = true, // 첫 대화 여부 (첫 대화인 경우 true)
    val timestamp: String = LocalDate.now().toString(),
)

@Serializable
data class MessageContent(
    var content: String? = null,
    val isFinish: Boolean = false, // 학습 종료 여부, 보내는 메세지는 false 고정
    val isLast: Boolean = false // 마지막 토큰 여부, 보내는 메세지는 false 고정
)