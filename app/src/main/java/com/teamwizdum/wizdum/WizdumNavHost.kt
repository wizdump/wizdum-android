package com.teamwizdum.wizdum

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.teamwizdum.wizdum.feature.login.loginScreen
import com.teamwizdum.wizdum.feature.onboarding.OnboardingViewModel
import com.teamwizdum.wizdum.feature.onboarding.navigation.onboardingScreen

@Composable
fun WizdumNavHost(
    navController: NavHostController,
    viewModel: OnboardingViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ONBOARDING.name
    ) {
        loginScreen()
        onboardingScreen(navController, viewModel)
    }
}