package com.teamwizdum.wizdum.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.teamwizdum.wizdum.feature.onboarding.navigation.navigateToInterest
import com.teamwizdum.wizdum.feature.quest.navigation.navigateToLecture

fun NavGraphBuilder.homeScreen(padding: PaddingValues, navController: NavHostController) {
    composable(route = HomeRoute.HOME) {
        HomeRoute(
            padding = padding,
            onNavigateToInterest = {
                navController.navigateToInterest()
            },
            onNavigateToLecture = { classId ->
                navController.navigateToLecture(classId)
            }
        )
    }
}

object HomeRoute {
    const val HOME = "HOME"
}