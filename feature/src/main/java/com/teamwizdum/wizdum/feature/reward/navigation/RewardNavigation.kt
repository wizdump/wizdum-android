package com.teamwizdum.wizdum.feature.reward.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teamwizdum.wizdum.feature.reward.RewardRoute

fun NavController.navigateToReward(lectureId: Int) {
    navigate(RewardRoute.rewardRoute(lectureId)) {
        popUpTo(graph.startDestinationId) { inclusive = false }
    }
}

fun NavGraphBuilder.rewardScreen(onNavigationBack: () -> Unit) {
    composable(
        route = RewardRoute.REWARD,
        arguments = listOf(navArgument("classId") { type = NavType.IntType })
    ) { backstackEntry ->
        val classId = backstackEntry.arguments?.getInt("classId") ?: 0

        RewardRoute(
            classId = classId,
            onNavigateToHome = onNavigationBack
        )
    }
}

object RewardRoute {
    fun rewardRoute(classId: Int) = "REWARD/$classId"
    val REWARD = "REWARD/{classId}"
}