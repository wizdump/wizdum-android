package com.teamwizdum.wizdum.feature.chat

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.chatScreen(
    navController: NavHostController,
    viewModel: ChatViewModel,
) {
    composable(
        route = "CHAT/{lectureId}/{orderSeq}/{lectureTitle}/{lectureStatus}/{isLastLecture}/{mentorName}/{mentorImgUrl}/{userName}",
        arguments = listOf(
            navArgument("lectureId") { type = NavType.IntType },
            navArgument("orderSeq") { type = NavType.IntType },
            navArgument("lectureTitle") { type = NavType.StringType },
            navArgument("lectureStatus") { type = NavType.StringType },
            navArgument("isLastLecture") { type = NavType.BoolType },
            navArgument("mentorName") { type = NavType.StringType },
            navArgument("mentorImgUrl") { type = NavType.StringType },
            navArgument("userName") { type = NavType.StringType }
        )
    ) { backstackEntry ->
        val lectureId = backstackEntry.arguments?.getInt("lectureId") ?: 0
        val orderSeq = backstackEntry.arguments?.getInt("orderSeq") ?: 0
        val lectureTitle = backstackEntry.arguments?.getString("lectureTitle") ?: ""
        val lectureStatus = backstackEntry.arguments?.getString("lectureStatus") ?: ""
        val isLastLecture = backstackEntry.arguments?.getBoolean("isLastLecture") ?: false
        val mentorName = backstackEntry.arguments?.getString("mentorName") ?: ""
        val mentorImgUrl = backstackEntry.arguments?.getString("mentorImgUrl") ?: ""
        val userName = backstackEntry.arguments?.getString("userName") ?: ""

        ChatScreen(lectureId = lectureId, orderSeq = orderSeq, lectureTitle = lectureTitle, lectureStatus = lectureStatus,
            isLastLecture = isLastLecture, mentorName = mentorName, mentorImgUrl = mentorImgUrl, userName = userName,
            viewModel = viewModel, onNavigateToClear = {
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