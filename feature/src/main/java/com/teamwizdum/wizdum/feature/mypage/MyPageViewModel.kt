package com.teamwizdum.wizdum.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.UserResponse
import com.teamwizdum.wizdum.data.repository.TokenRepository
import com.teamwizdum.wizdum.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _userInfo = MutableStateFlow<UserResponse>(UserResponse())
    val userInfo: StateFlow<UserResponse> = _userInfo

    fun getUserInfo() {
        viewModelScope.launch {
            userRepository.getUserInfo().collect {
                _userInfo.value = it
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            userRepository.logout().collect {
                tokenRepository.deleteTokens()
                onSuccess()
            }
        }
    }

    fun withdraw(onSuccess: () -> Unit) {
        viewModelScope.launch {
            userRepository.withdraw().collect {
                tokenRepository.deleteTokens()
                onSuccess()
            }
        }
    }

    companion object {
        const val PRIVACY_POLICY = "https://sites.google.com/view/wizdum-privacy"
        const val TERMS_OF_SERVICE = "https://sites.google.com/view/wizdum-service"
    }
}