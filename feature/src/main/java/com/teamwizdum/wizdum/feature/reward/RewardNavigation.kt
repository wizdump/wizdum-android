package com.teamwizdum.wizdum.feature.reward

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.rewardScreen(navController: NavHostController, viewModel: RewardViewModel) {
    composable(
        route = "REWARD/{lectureId}",
        arguments = listOf(navArgument("lectureId") { type = NavType.IntType })
    ) { backstackEntry ->
        val lectureId = backstackEntry.arguments?.getInt("lectureId") ?: 0

        RewardScreen(lectureId = lectureId, viewModel = viewModel)
    }
}