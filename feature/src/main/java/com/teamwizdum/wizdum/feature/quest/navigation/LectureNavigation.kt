package com.teamwizdum.wizdum.feature.quest.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teamwizdum.wizdum.feature.chat.navigation.navigateToChat
import com.teamwizdum.wizdum.feature.common.extensions.decodeFromUri
import com.teamwizdum.wizdum.feature.common.extensions.encodeToUri
import com.teamwizdum.wizdum.feature.quest.LectureAllClearRoute
import com.teamwizdum.wizdum.feature.quest.LectureClearScreen
import com.teamwizdum.wizdum.feature.quest.LectureRoute
import com.teamwizdum.wizdum.feature.quest.navigation.argument.LectureClearArgument
import kotlinx.serialization.json.Json

fun NavController.navigateToLecture(classId: Int) {
    navigate(LectureRoute.lectureMainRoute(classId))
}

fun NavController.navigateToLectureClear(lectureInfo: LectureClearArgument) {
    navigate(LectureRoute.lectureClearRoute(Json.encodeToUri(lectureInfo)))
}

fun NavController.navigateToLectureAllClear(lectureId: Int, mentorName: String) {
    navigate(LectureRoute.lectureAllClearRoute(lectureId, mentorName))
}

fun NavGraphBuilder.lectureScreen(navController: NavHostController) {
    composable(
        route = LectureRoute.LECTURE,
        arguments = listOf(
            navArgument("classId") { type = NavType.IntType }
        )
    ) { backstackEntry ->
        val classId = backstackEntry.arguments?.getInt("classId") ?: 0

        LectureRoute(
            classId = classId,
            onNavigateToChat = { lectureInfo ->
                navController.navigateToChat(lectureInfo)
            },
            onNavigateToLectureAllClear = { lectureId, mentorName ->
                navController.navigateToLectureAllClear(lectureId, mentorName)
            }
        )
    }

    composable(
        route = LectureRoute.LECTURE_CLEAR,
        arguments = listOf(
            navArgument("clearedLectureInfo") { type = NavType.StringType }
        )
    ) { backstackEntry ->
        val lectureInfo = backstackEntry.arguments?.getString("clearedLectureInfo") ?: ""

        LectureClearScreen(
            lectureInfo = Json.decodeFromUri(lectureInfo),
            onNavigateToLecture = {
                //navController.navigateToLecture()
                // popBack()
            }
        )
    }

    composable(
        route = LectureRoute.LECTURE_ALL_CLEAR,
        arguments = listOf(
            navArgument("lectureId") { type = NavType.IntType },
            navArgument("mentorName") { type = NavType.StringType }
        )
    ) { backstackEntry ->
        val lectureId = backstackEntry.arguments?.getInt("lectureId") ?: 0
        val mentorName = backstackEntry.arguments?.getString("mentorName") ?: ""

        LectureAllClearRoute(
            lectureId = lectureId,
            mentorName = mentorName,
            onNavigateToReward = {
                navController.navigate("REWARD/$lectureId")
            }
        )
    }
}

object LectureRoute {
    fun lectureMainRoute(classId: Int) = "LECTURE/$classId"
    const val LECTURE = "LECTURE/{classId}"

    fun lectureClearRoute(clearedLectureInfo: String) = "LECTURE_CLEAR/$clearedLectureInfo"
    const val LECTURE_CLEAR = "LECTURE_CLEAR/{clearedLectureInfo}"

    fun lectureAllClearRoute(lectureId: Int, mentorName: String) =
        "LECTURE_ALL_CLEAR/$lectureId/$mentorName"

    const val LECTURE_ALL_CLEAR = "LECTURE_ALL_CLEAR/{categoryId}"
}