package com.teamwizdum.wizdum.feature.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.loginScreen() {
    composable(route= "LOGIN") {
        LoginScreen()
    }
}