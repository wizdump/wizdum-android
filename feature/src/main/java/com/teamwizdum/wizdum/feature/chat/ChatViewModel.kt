package com.teamwizdum.wizdum.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.data.model.MessageContent
import com.teamwizdum.wizdum.data.repository.WebSocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: WebSocketRepository,
) : ViewModel() {

    private val _message = MutableStateFlow<List<ChatMessage>>(emptyList())
    val message: StateFlow<List<ChatMessage>> = _message.asStateFlow()

    var isFirst = true

    fun initialize() {
        repository.connect()
        viewModelScope.launch {
            var receivedMessage = StringBuilder()

            repository.observeMessage().collect { message ->
                if (!message.message.isLast) {
                    Timber.d("토큰 ${message.message.content}")
                    receivedMessage.append(message.message.content)

                } else {
                    var lastMessage = message
                    lastMessage.message.content = receivedMessage.toString()
                    _message.value += lastMessage

                    receivedMessage.clear()
                }
            }
        }
    }

    fun sendMessage(messageData: ChatMessage) {
        repository.sendMessage(messageData)
        _message.value += messageData
    }

    override fun onCleared() {
        super.onCleared()
        repository.close()
    }
}