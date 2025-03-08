package com.teamwizdum.wizdum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val checkOnboarding = true
    private val accessToken = tokenRepository.getAccessToken()

    private val _mainState = MutableStateFlow<MainState>(MainState.None)
    val mainState: StateFlow<MainState> = _mainState

    init {
        navigate()
    }

    private fun navigate() {
        // 1. onboarding 여부 체크
        if (!checkOnboarding) {
            _mainState.value = MainState.Onboarding
            return
        }

        // 2. accessToken 여부 체크
        if (accessToken.isNullOrEmpty()) {
            _mainState.value = MainState.Login
        } else {
            // 로그인 시도
            wizdumLogin()
        }
    }

    private fun wizdumLogin() {
        viewModelScope.launch {
            // 성공 -> 홈화면
            // 실패 -> 로그인 화면
            _mainState.value = MainState.Home
        }
    }
}