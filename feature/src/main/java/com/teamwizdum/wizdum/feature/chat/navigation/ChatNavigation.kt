package com.teamwizdum.wizdum.feature.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teamwizdum.wizdum.feature.chat.ChatRoute
import com.teamwizdum.wizdum.feature.common.extensions.decodeFromUri
import com.teamwizdum.wizdum.feature.common.extensions.encodeToUri
import com.teamwizdum.wizdum.feature.quest.navigation.argument.LectureArgument
import com.teamwizdum.wizdum.feature.quest.navigation.navigateToLectureAllClear
import com.teamwizdum.wizdum.feature.quest.navigation.navigateToLectureClear
import kotlinx.serialization.json.Json

fun NavController.navigateToChat(lectureInfo: LectureArgument) {
    navigate(ChatRoute.chatRoute(Json.encodeToUri(lectureInfo)))
}

fun NavGraphBuilder.chatScreen(navController: NavHostController) {
    composable(
        route = ChatRoute.CHAT,
        arguments = listOf(
            navArgument("lectureInfo") { type = NavType.StringType }
        )
    ) { backstackEntry ->
        val lectureInfo = backstackEntry.arguments?.getString("lectureInfo") ?: ""

        ChatRoute(
            lectureInfo = Json.decodeFromUri(lectureInfo),
            onBackPressed = {
                navController.popBackStack()
            },
            onNavigateToClear = { clearedLectureInfo ->
                navController.navigateToLectureClear(clearedLectureInfo)
            },
            onNavigateToAllClear = { id, name ->
                navController.navigateToLectureAllClear(id, name)
            }
        )
    }
}

object ChatRoute {
    fun chatRoute(lectureInfo: String) = "CHAT/$lectureInfo"
    val CHAT = "CHAT/{lectureInfo}"
}
