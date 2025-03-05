package com.teamwizdum.wizdum.feature.quest.navigation

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teamwizdum.wizdum.feature.quest.QuestAllClearScreen
import com.teamwizdum.wizdum.feature.quest.QuestScreen
import com.teamwizdum.wizdum.feature.quest.QuestViewModel

fun NavGraphBuilder.questScreen(navController: NavHostController, viewModel: QuestViewModel) {
    composable(route = "QUEST") {
        QuestScreen(
            viewModel = viewModel,
            onNavigateToChat = {
                navController.navigate("CHAT/${viewModel.chatRoomInfo.lectureId}/${viewModel.chatRoomInfo.orderSeq}/${Uri.encode(viewModel.chatRoomInfo.lectureTitle)}/${viewModel.chatRoomInfo.lectureStatus}/${viewModel.chatRoomInfo.isLastLecture}/${Uri.encode(viewModel.chatRoomInfo.mentorName)}/${Uri.encode(viewModel.chatRoomInfo.mentorImgUrl)}/${viewModel.chatRoomInfo.userName}")
            },
            onNavigateToQuestALlClear = { lectureId, mentorName ->
                navController.navigate("QUEST_ALL_CLEAR/$lectureId/$mentorName")
            }
        )
    }

    composable(
        route = "QUEST_ALL_CLEAR/{lectureId}/{mentorName}",
        arguments = listOf(
            navArgument("lectureId") { type = NavType.IntType },
            navArgument("mentorName") { type = NavType.StringType }
        )
    ) { backstackEntry ->
        val lectureId = backstackEntry.arguments?.getInt("lectureId") ?: 0
        val mentorName = backstackEntry.arguments?.getString("mentorName") ?: ""

        QuestAllClearScreen(lectureId = lectureId, mentorName = mentorName) {
            viewModel.postReward(lectureId) {
                navController.navigate("REWARD/$lectureId")
            }
        }
    }
}