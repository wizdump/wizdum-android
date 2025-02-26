package com.teamwizdum.wizdum.feature.quest

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.questScreen(navController: NavHostController) {
    composable(route = "QUEST") {
        QuestScreen()
    }
}