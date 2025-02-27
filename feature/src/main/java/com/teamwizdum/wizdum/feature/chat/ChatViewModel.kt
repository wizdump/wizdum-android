package com.teamwizdum.wizdum.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.repository.WebSocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: WebSocketRepository,
) : ViewModel() {

    private val _message = MutableStateFlow("응답 대기중...")
    val message: StateFlow<String> = _message.asStateFlow()

    fun initialize() {
        repository.connect()
        viewModelScope.launch {
            repository.observeMessage().collect { message ->
                _message.value = message
            }
        }
    }

    fun sendMessage(questId: Int, message: String) {
        repository.sendMessage(questId, message)
    }

    override fun onCleared() {
        super.onCleared()
        repository.close()
    }
}