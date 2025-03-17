package com.teamwizdum.wizdum.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.data.model.MessageContent
import com.teamwizdum.wizdum.data.repository.QuestRepository
import com.teamwizdum.wizdum.data.repository.TokenRepository
import com.teamwizdum.wizdum.data.repository.WebSocketRepository
import com.teamwizdum.wizdum.feature.chat.info.MessageType
import com.teamwizdum.wizdum.feature.common.base.UiState
import com.teamwizdum.wizdum.feature.common.enums.LectureStatus
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

    private val _chatUiState = MutableStateFlow<UiState<List<ChatMessage>>>(UiState.Loading)
    val chatUiState: StateFlow<UiState<List<ChatMessage>>> = _chatUiState.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isReceiving = MutableStateFlow(false)
    val isReceiving: StateFlow<Boolean> = _isReceiving

    private val _isFinish = MutableStateFlow(false)
    val isFinish: StateFlow<Boolean> = _isFinish

    private var receivedMessage = StringBuilder()

    fun initialize(lectureId: Int) {
        webSocketRepository.connect()

        viewModelScope.launch(Dispatchers.Main) {
            getChatList(lectureId)

            webSocketRepository.observeMessage().collect { chat ->
                if (chat.message.isLast) {
                    chat.message.content = receivedMessage.toString()
                    _messages.value += chat

                    receivedMessage.clear()
                    _isReceiving.value = false
                    _isFinish.value = isChatFinished(chat)
                } else {
                    if (!_isReceiving.value) {
                        receivedMessage.clear()
                    }
                    receivedMessage.append(chat.message.content)
                    _isReceiving.value = true
                }
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
            _messages.value += senderMessage
        }
    }

    private fun getChatList(lectureId: Int) {
        viewModelScope.launch {
            questRepository.getChatList(lectureId)
                .onSuccess { data ->
                    _chatUiState.value = UiState.Success(data)
                    _messages.value = data
                }.onFailure {

                }
        }
    }

    fun finishQuest(lectureId: Int, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            questRepository.finishQuest(lectureId)
                .onSuccess {
                    onSuccess(it.encouragement)
                }.onFailure { }
        }
    }

    fun setReceiving(isReceiving: Boolean) {
        _isReceiving.value = isReceiving
    }

    fun isChatOngoing(lectureStatus: String): Boolean {
        if (lectureStatus == LectureStatus.DONE.name) {
            return false
        }

        val lastMessage = _messages.value.lastOrNull()?.message
        return lastMessage?.let { it.isFinish && it.isOngoing } ?: false
    }

    private fun isChatFinished(chat: ChatMessage): Boolean {
        return chat.message.isFinish && !chat.message.isOngoing
    }

    fun shouldShowFinishState(lectureStatus: String): Boolean {
        if (lectureStatus == LectureStatus.DONE.name || messages.value.isEmpty()) {
            return false
        }

        val lastMessage = _messages.value.lastOrNull()?.message
        return lastMessage?.let { it.isFinish && !it.isOngoing } ?: false
    }

    override fun onCleared() {
        super.onCleared()
        webSocketRepository.close()
    }
}