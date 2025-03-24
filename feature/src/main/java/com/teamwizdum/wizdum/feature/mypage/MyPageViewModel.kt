package com.teamwizdum.wizdum.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.UserResponse
import com.teamwizdum.wizdum.data.repository.TokenRepository
import com.teamwizdum.wizdum.data.repository.UserRepository
import com.teamwizdum.wizdum.feature.common.base.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<MyPageUiEvent>()
    val eventFlow: SharedFlow<MyPageUiEvent> = _eventFlow

    fun getUserInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            userRepository.getUserInfo()
                .onSuccess { data ->
                    _uiState.update { it.copy(userInfo = data) }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            handleException = ErrorState.DisplayError(
                                throwable = throwable,
                                retry = ::getUserInfo
                            )
                        )
                    }
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            userRepository.logout()
                .onSuccess {
                    tokenRepository.deleteTokens()
                    _eventFlow.emit(MyPageUiEvent.NavigateToMainActivity)
                }
                .onFailure { throwable ->
                    _eventFlow.emit(
                        MyPageUiEvent.ShowErrorDialog(
                            throwable = throwable,
                            retry = ::logout
                        )
                    )
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun withdraw() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            userRepository.withdraw()
                .onSuccess {
                    tokenRepository.deleteTokens()
                    _eventFlow.emit(MyPageUiEvent.NavigateToMainActivity)
                }
                .onFailure { throwable ->
                    _eventFlow.emit(
                        MyPageUiEvent.ShowErrorDialog(
                            throwable = throwable,
                            retry = ::withdraw
                        )
                    )
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    companion object {
        const val PRIVACY_POLICY = "https://sites.google.com/view/wizdum-privacy"
        const val TERMS_OF_SERVICE = "https://sites.google.com/view/wizdum-service"
    }

    data class MyPageUiState(
        val isLoading: Boolean = false,
        val userInfo: UserResponse = UserResponse(),
        val handleException: ErrorState = ErrorState.None,
    )

    sealed interface MyPageUiEvent {
        data object NavigateToMainActivity : MyPageUiEvent
        data class ShowErrorDialog(val throwable: Throwable, val retry: () -> Unit) : MyPageUiEvent
    }
}