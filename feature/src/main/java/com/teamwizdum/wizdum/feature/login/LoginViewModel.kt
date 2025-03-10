package com.teamwizdum.wizdum.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.repository.QuestRepository
import com.teamwizdum.wizdum.data.repository.TokenRepository
import com.teamwizdum.wizdum.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository,
    private val questRepository: QuestRepository,
) : ViewModel() {

    var classId: Int = -1

    fun login(
        accessToken: String,
        moveToLecture: () -> Unit,
        moveToHome: () -> Unit,
    ) {
        viewModelScope.launch {
            val token = tokenRepository.getAccessToken()

            if (!token.isNullOrEmpty()) {
                checkRegisteredUser {
                    if (classId != -1) {
                        startLecture(classId) {
                            moveToLecture()
                        }
                    } else {
                        moveToHome()
                    }
                }

                return@launch
            }

            userRepository.signUp(accessToken).collect {
                tokenRepository.saveTokens(
                    accessToken = it.accessToken,
                    refreshToken = it.refreshToken
                )

                moveToHome()

                Timber.d("accessToken 저장됨 : ${tokenRepository.getAccessToken()}")
            }
        }
    }

    private fun checkRegisteredUser(onSuccess: () -> Unit) {
        viewModelScope.launch {
            userRepository.login().collect {
                onSuccess()
            }
        }
    }

    private fun startLecture(classId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            questRepository.startQuest(classId = classId).collect {
                onSuccess()
            }
        }
    }
}