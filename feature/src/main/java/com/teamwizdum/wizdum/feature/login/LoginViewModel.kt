package com.teamwizdum.wizdum.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.repository.DataStoreRepository
import com.teamwizdum.wizdum.data.repository.QuestRepository
import com.teamwizdum.wizdum.data.repository.TokenRepository
import com.teamwizdum.wizdum.data.repository.UserRepository
import com.teamwizdum.wizdum.feature.common.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository,
    private val questRepository: QuestRepository,
) : ViewModel() {

    var classId: Int = -1
    var nickName: String = ""

    private val _loginState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val loginState: StateFlow<UiState<Unit>> = _loginState

    fun login(
        accessToken: String,
        moveToLecture: () -> Unit,
        moveToHome: () -> Unit,
    ) {
        viewModelScope.launch {
            val token = tokenRepository.getAccessToken()

            if (!token.isNullOrEmpty()) {
                checkRegisteredUser {
                    navigation(moveToLecture, moveToHome)
                }

                return@launch
            }

            userRepository.signUp(accessToken)
                .onSuccess {
                    tokenRepository.saveTokens(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken
                    )

                    setOnboardingCompleted()

                    _loginState.value = UiState.Success(Unit)
                    delay(2000) // 로그인 완료 화면을 보여주기 위한 딜레이

                    navigation(moveToLecture, moveToHome)

                    Timber.d("accessToken 저장됨 : ${tokenRepository.getAccessToken()}")
                }.onFailure { error ->
                    _loginState.value = UiState.Failed(error.message)
                }
        }
    }

    private fun navigation(
        moveToLecture: () -> Unit,
        moveToHome: () -> Unit,
    ) {
        if (classId != -1) {
            startLecture(classId) {
                moveToLecture()
            }
        } else {
            moveToHome()
        }
    }

    private fun checkRegisteredUser(onSuccess: () -> Unit) {
        viewModelScope.launch {
            userRepository.login()
                .onSuccess {
                    _loginState.value = UiState.Success(Unit)
                    delay(2000) // 로그인 완료 화면을 보여주기 위한 딜레이
                    onSuccess()
                }.onFailure { error ->
                    _loginState.value = UiState.Failed(error.message)
                }
        }
    }

    private fun startLecture(classId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            questRepository.startQuest(classId = classId)
                .onSuccess {
                    onSuccess()
                }.onFailure { error ->
                    _loginState.value = UiState.Failed(error.message)
                }
        }
    }

    private fun setOnboardingCompleted() {
        viewModelScope.launch {
            dataStoreRepository.setOnboardingCompleted(true) // set 메서드만 의존성 문제 발생
            Timber.d("hasSeenCheck in Login : ${dataStoreRepository.isOnboardingCompleted()}")
        }
    }
}