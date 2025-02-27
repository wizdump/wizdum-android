package com.teamwizdum.wizdum.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.teamwizdum.wizdum.feature.onboarding.KeywordSelectionScreen
import com.teamwizdum.wizdum.feature.onboarding.MentorDetailScreen
import com.teamwizdum.wizdum.feature.onboarding.MentorMatchScreen
import com.teamwizdum.wizdum.feature.onboarding.OnboardingViewModel
import com.teamwizdum.wizdum.feature.onboarding.QuestionSelectionScreen
import com.teamwizdum.wizdum.feature.onboarding.StartScreen

fun NavGraphBuilder.onboardingScreen(navController: NavHostController, viewModel: OnboardingViewModel) {
    composable(route = "ONBOARDING") {
        StartScreen() {
            navController.navigate(route = "KEYWORD")
        }
    }

    composable(route = "KEYWORD") {
        KeywordSelectionScreen(viewModel = viewModel) {
            navController.navigate(route = "GOAL")
            //navController.navigate(route = "CHAT")
        }
    }

    composable(route = "GOAL") {
        QuestionSelectionScreen(viewModel = viewModel) {
            navController.navigate(route = "MENTOR")
        }
    }

    composable(route = "MENTOR") {
        MentorMatchScreen(viewModel = viewModel) {
            navController.navigate(route = "METOR_DETAIL")
        }
    }

    composable(route = "METOR_DETAIL") {
        MentorDetailScreen() {
            navController.navigate(route = "LOGIN")
        }
    }
}