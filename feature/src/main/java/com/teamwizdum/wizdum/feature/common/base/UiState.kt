package com.teamwizdum.wizdum.feature.common.base

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Failed(val e: Throwable, val retry: () -> Unit) : UiState<Nothing>()
}