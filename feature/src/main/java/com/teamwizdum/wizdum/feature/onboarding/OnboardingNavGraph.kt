package com.teamwizdum.wizdum.feature.onboarding

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.onboardingScreen(navController: NavHostController, viewModel: OnboardingViewModel) {
    composable(route = "ONBOARDING") {
        StartScreen() {
            navController.navigate(route = "KEYWORD")
        }
    }

    composable(route = "KEYWORD") {
        KeywordSelectionScreen(viewModel = viewModel) {
            navController.navigate(route = "GOAL")
        }
    }

    composable(route = "GOAL") {
        QuestionSelectionScreen() {
            navController.navigate(route = "MENTOR")
        }
    }

    composable(route = "MENTOR") {
        MentorMatchScreen() {
            navController.navigate(route = "METOR_DETAIL")
        }
    }

    composable(route = "METOR_DETAIL") {
        MentorDetailScreen() {
            navController.navigate(route = "LOGIN")
        }
    }
}