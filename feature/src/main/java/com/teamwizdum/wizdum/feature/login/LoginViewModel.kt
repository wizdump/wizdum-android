package com.teamwizdum.wizdum.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.repository.DataStoreRepository
import com.teamwizdum.wizdum.data.repository.LectureRepository
import com.teamwizdum.wizdum.data.repository.TokenRepository
import com.teamwizdum.wizdum.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository,
    private val lectureRepository: LectureRepository,
) : ViewModel() {

    var classId: Int = -1
    var nickName: String = ""

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow: SharedFlow<LoginUiEvent> = _eventFlow.asSharedFlow()

    fun login(accessToken: String) {
        viewModelScope.launch {
            val token = tokenRepository.getAccessToken()

            if (!token.isNullOrEmpty()) {
                checkRegisteredUser()

                return@launch
            }

            signUp(accessToken)
        }
    }

    private fun navigation() {
        viewModelScope.launch {
            if (classId != -1) {
                startLecture(classId)
            } else {
                _eventFlow.emit(LoginUiEvent.NavigateToHome)
            }
        }
    }

    private fun signUp(accessToken: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            userRepository.signUp(accessToken)
                .onSuccess {
                    tokenRepository.saveTokens(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken
                    )

                    setOnboardingCompleted()

                    _uiState.update { it.copy(login = true) }
                    delay(2000) // 로그인 완료 화면을 보여주기 위한 딜레이

                    navigation()

                    Timber.d("accessToken 저장됨 : ${tokenRepository.getAccessToken()}")
                }.onFailure { throwable ->
                    _eventFlow.emit(
                        LoginUiEvent.ShowErrorDialog(
                            throwable = throwable,
                            retry = { login(accessToken) }
                        )
                    )
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun checkRegisteredUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            userRepository.login()
                .onSuccess {
                    _uiState.update { it.copy(login = true) }
                    delay(2000) // 로그인 완료 화면을 보여주기 위한 딜레이

                    navigation()
                }.onFailure { throwable ->
                    _eventFlow.emit(
                        LoginUiEvent.ShowErrorDialog(
                            throwable = throwable,
                            retry = ::checkRegisteredUser
                        )
                    )
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun startLecture(classId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            lectureRepository.startLecture(classId = classId)
                .onSuccess {
                    _eventFlow.emit(LoginUiEvent.NavigateToLecture)
                }.onFailure { throwable ->
                    _eventFlow.emit(
                        LoginUiEvent.ShowErrorDialog(
                            throwable = throwable,
                            retry = { startLecture(classId) }
                        )
                    )
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun setOnboardingCompleted() {
        viewModelScope.launch {
            dataStoreRepository.setOnboardingCompleted(true)
            Timber.d("hasSeenCheck in Login : ${dataStoreRepository.isOnboardingCompleted()}")
        }
    }

    fun showToast(message: String) {
        viewModelScope.launch {
            _eventFlow.emit(LoginUiEvent.ShowToast(message))
        }
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val login: Boolean = false,
)

sealed interface LoginUiEvent {
    object NavigateToLecture : LoginUiEvent
    object NavigateToHome : LoginUiEvent
    data class ShowErrorDialog(val throwable: Throwable, val retry: () -> Unit) : LoginUiEvent
    data class ShowToast(val message: String): LoginUiEvent
}