package com.teamwizdum.wizdum

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.teamwizdum.wizdum.feature.login.loginScreen

@Composable
fun WizdumNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN.name
    ) {
        loginScreen()
    }
}