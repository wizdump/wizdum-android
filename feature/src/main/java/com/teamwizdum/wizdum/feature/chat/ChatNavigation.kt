package com.teamwizdum.wizdum.feature.chat

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.teamwizdum.wizdum.feature.quest.QuestAllClearScreen

fun NavGraphBuilder.chatScreen(navController: NavHostController, viewModel: ChatViewModel) {
    composable(route = "CHAT") {
        ChatScreen(viewModel = viewModel, onNavigateToClear = {
            navController.navigate("CLEAR")
        })
    }

    composable(route = "CLEAR") {
        QuestClearScreen(viewModel = viewModel, onNavigateToQuest = {
            navController.navigate("QUEST")
        })
    }

//    composable(route = "ALL_CLEAR") {
//        QuestAllClearScreen(onNavigateNext = {
//            navController.navigate("REWARD")
//        })
//    }
}