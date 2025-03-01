package com.teamwizdum.wizdum.feature.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.loginScreen(navController: NavHostController, viewModel: LoginViewModel) {
    composable(route= "LOGIN") {
        LoginScreen(viewModel = viewModel) {
            navController.navigate("QUEST")
        }
    }
}