package com.teamwizdum.wizdum.feature.chat

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.chatScreen(navController: NavHostController, viewModel: ChatViewModel) {
    composable(route = "CHAT") {
        ChatScreen(viewModel = viewModel, onNavigateToClear = {
            navController.navigate("ALL_CLEAR")
        })
    }

    composable(route = "CLEAR") {
        QuestClearScreen()
    }

    composable(route = "ALL_CLEAR") {
        QuestAllClearScreen()
    }
}