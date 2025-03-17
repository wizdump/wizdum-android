package com.teamwizdum.wizdum.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.teamwizdum.wizdum.feature.home.HomeRoute

fun NavController.navigateHome() {
    navigate(HomeRoute.HOME) {
        popUpTo(HomeRoute.HOME)
        launchSingleTop = true
    }
}

fun NavGraphBuilder.homeScreen(
    padding: PaddingValues,
    onNavigateToInterest: () -> Unit,
    onNavigateToLecture: (Int) -> Unit,
) {
    composable(route = HomeRoute.HOME) {
        HomeRoute(
            padding = padding,
            onNavigateToInterest = onNavigateToInterest,
            onNavigateToLecture = { classId ->
                onNavigateToLecture(classId)
            }
        )
    }
}

object HomeRoute {
    const val HOME = "HOME"
}