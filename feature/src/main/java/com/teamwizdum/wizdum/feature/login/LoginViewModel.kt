package com.teamwizdum.wizdum.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
) : ViewModel() {

    fun login(accessToken: String) {
        viewModelScope.launch {
            userRepository.login(accessToken).collect {
                tokenRepository.saveTokens(
                    accessToken = it.accessToken,
                    refreshToken = it.refreshToken
                )

                Timber.d("accessToken 저장됨 : ${tokenRepository.getAccessToken()}")
            }
        }
    }
}