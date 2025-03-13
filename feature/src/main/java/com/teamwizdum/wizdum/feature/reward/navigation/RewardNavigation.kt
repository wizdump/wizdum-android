package com.teamwizdum.wizdum.feature.reward.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teamwizdum.wizdum.feature.reward.RewardRoute
import com.teamwizdum.wizdum.feature.reward.RewardViewModel

fun NavController.navigateToReward(lectureId: Int) {
    navigate(RewardRoute.rewardRoute(lectureId))
}

fun NavGraphBuilder.rewardScreen(navController: NavHostController) {
    composable(
        route = RewardRoute.REWARD,
        arguments = listOf(navArgument("lectureId") { type = NavType.IntType })
    ) { backstackEntry ->
        val lectureId = backstackEntry.arguments?.getInt("lectureId") ?: 0

        RewardRoute(
            lectureId = lectureId,
            onNavigateToHome = {
                navController.navigate("HOME") {
                    popUpTo(navController.graph.startDestinationId) {inclusive = false}
                }
            }
        )
    }
}

object RewardRoute {
    fun rewardRoute(lectureId: Int) = "REWARD/$lectureId"
    val REWARD = "REWARD/{lectureId}"
}