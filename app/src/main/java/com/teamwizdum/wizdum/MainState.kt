package com.teamwizdum.wizdum

sealed class MainState {
    data object None : MainState()
    data object Onboarding : MainState()
    data object Login : MainState()
    data object Home : MainState()
}