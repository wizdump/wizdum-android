package com.teamwizdum.wizdum.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.data.model.MessageContent
import com.teamwizdum.wizdum.data.repository.TokenRepository
import com.teamwizdum.wizdum.data.repository.WebSocketRepository
import com.teamwizdum.wizdum.feature.chat.info.MessageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: WebSocketRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val token = tokenRepository.getAccessToken() ?: ""

    private val _message = MutableStateFlow<List<ChatMessage>>(emptyList())
    val message: StateFlow<List<ChatMessage>> = _message.asStateFlow()

    private var receivedMessage = StringBuilder()

    fun initialize() {
        repository.connect()

        viewModelScope.launch(Dispatchers.Main) {

            repository.observeMessage().collect { message ->
                if (!message.message.isLast) {
                    receivedMessage.append(message.message.content)

                } else {
                    val totalMessage = message
                    totalMessage.message.content = receivedMessage.toString()
                    _message.value += totalMessage

                    receivedMessage.clear()
                }
            }
        }
    }

    fun sendMessage(name: String, lectureId: Int, message: String) {
        // TODO: 토큰 만료되는 경우 생각해보기
        val senderMessage = ChatMessage(
            type = MessageType.USER_REQUEST.name,
            lectureId = lectureId,
            name = name,
            accessToken = token,
            message = MessageContent(content = message),
            isHide = false,
        )
        repository.sendMessage(senderMessage)
        _message.value += senderMessage
    }

    override fun onCleared() {
        super.onCleared()
        repository.close()
    }
}