package com.teamwizdum.wizdum.feature.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.loginScreen(viewModel: LoginViewModel) {
    composable(route= "LOGIN") {
        LoginScreen()
    }
}