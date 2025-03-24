package com.teamwizdum.wizdum.feature.common.base

sealed class ErrorState {
    object None : ErrorState()
    data class DisplayError(val throwable: Throwable, val retry: () -> Unit) : ErrorState()
}