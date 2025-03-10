package com.teamwizdum.wizdum.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.data.model.MessageContent
import com.teamwizdum.wizdum.data.repository.QuestRepository
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
    private val webSocketRepository: WebSocketRepository,
    private val tokenRepository: TokenRepository,
    private val questRepository: QuestRepository,
) : ViewModel() {

    private val token = tokenRepository.getAccessToken() ?: ""

    private val _message = MutableStateFlow<List<ChatMessage>>(emptyList())
    val message: StateFlow<List<ChatMessage>> = _message.asStateFlow()

    private val _isReceiving = MutableStateFlow(false)
    val isReceiving: StateFlow<Boolean> = _isReceiving

    private var receivedMessage = StringBuilder()

    fun initialize(lectureId: Int) {
        webSocketRepository.connect()

        viewModelScope.launch(Dispatchers.Main) {

            getChatList(lectureId)

            webSocketRepository.observeMessage().collect { message ->
                if (message.message.isLast) {
                    message.message.content = receivedMessage.toString()
                    _message.value += message

                    receivedMessage.clear()
                    _isReceiving.value = false
                } else {
                    if (!_isReceiving.value) {
                        receivedMessage.clear()
                    }
                    receivedMessage.append(message.message.content)
                    _isReceiving.value = true
                }
//                if (!message.message.isLast) {
//                    receivedMessage.append(message.message.content)
//                    _isReceiving.value = true
//
//                } else {
//                    val totalMessage = message
//                    totalMessage.message.content = receivedMessage.toString()
//                    _message.value += totalMessage
//
//                    receivedMessage.clear()
//                    _isReceiving.value = false
//                }
            }
        }
    }

    fun sendMessage(name: String, lectureId: Int, message: String, isHide: Boolean = false) {
        // TODO: 토큰 만료되는 경우 생각해보기
        val senderMessage = ChatMessage(
            type = MessageType.USER_REQUEST.name,
            lectureId = lectureId,
            name = name,
            accessToken = token,
            message = MessageContent(content = message),
            isHide = isHide,
        )
        webSocketRepository.sendMessage(senderMessage)

        if (!isHide) {
            _message.value += senderMessage
        }
    }

    fun getChatList(lectureId: Int) {
        viewModelScope.launch {
            questRepository.getChatList(lectureId).collect {
                questRepository.getChatList(lectureId).collect {
                    _message.value = it
                }
            }
        }
    }

    fun finishQuest(lectureId: Int, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            questRepository.finishQuest(lectureId).collect {
                onSuccess(it.encouragement)
            }
        }
    }

    fun setReceiving(isReceiving: Boolean) {
        _isReceiving.value = isReceiving
    }

    override fun onCleared() {
        super.onCleared()
        webSocketRepository.close()
    }
}