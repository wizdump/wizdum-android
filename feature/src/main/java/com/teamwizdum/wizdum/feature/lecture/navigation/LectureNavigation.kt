package com.teamwizdum.wizdum.feature.lecture.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teamwizdum.wizdum.feature.common.extensions.decodeFromUri
import com.teamwizdum.wizdum.feature.common.extensions.encodeToUri
import com.teamwizdum.wizdum.feature.home.navigation.HomeRoute
import com.teamwizdum.wizdum.feature.lecture.LectureAllClearRoute
import com.teamwizdum.wizdum.feature.lecture.LectureClearScreen
import com.teamwizdum.wizdum.feature.lecture.LectureRoute
import com.teamwizdum.wizdum.feature.lecture.navigation.argument.LectureArgument
import com.teamwizdum.wizdum.feature.lecture.navigation.argument.LectureClearArgument
import kotlinx.serialization.json.Json

fun NavController.navigateToLectureWithHomeRoot(classId: Int) {
    // Home 화면 root 설정을 위한 navigate
    navigate(HomeRoute.HOME) {
        popUpTo(graph.id) { inclusive = false } // 기존 모든 스택 제거
        launchSingleTop = true
    }

    // 그다음 Lecture 이동
    navigate(LectureRoute.lectureMainRoute(classId))
}

fun NavController.navigateToLecture(classId: Int) {
    navigate(LectureRoute.lectureMainRoute(classId))
}

fun NavController.navigateToLectureClear(lectureInfo: LectureClearArgument) {
    navigate(LectureRoute.lectureClearRoute(Json.encodeToUri(lectureInfo))) {
        val lectureMainRouteId = graph.findNode(LectureRoute.LECTURE)?.id ?: 0
        popUpTo(lectureMainRouteId) {
            inclusive = false
        }
    }
}

fun NavController.navigateToLectureAllClear(classId: Int, mentorName: String) {
    navigate(LectureRoute.lectureAllClearRoute(classId, mentorName)) {
        val lectureMainRouteId = graph.findNode(LectureRoute.LECTURE)?.id ?: 0
        popUpTo(lectureMainRouteId) {
            inclusive = false
        }
    }
}

fun NavGraphBuilder.lectureScreen(
    onNavigateBack: () -> Unit,
    onNavigateToLecture: (Int) -> Unit,
    onNavigateToChat: (LectureArgument) -> Unit,
    onNavigateToAllClear: (Int, String) -> Unit,
    onNavigateToReward: (Int) -> Unit,
) {
    composable(
        route = LectureRoute.LECTURE,
        arguments = listOf(
            navArgument("classId") { type = NavType.IntType }
        )
    ) { backstackEntry ->
        val classId = backstackEntry.arguments?.getInt("classId") ?: 0

        LectureRoute(
            classId = classId,
            onNavigateBack = onNavigateBack,
            onNavigateToChat = { lectureInfo ->
                onNavigateToChat(lectureInfo)
            },
            onNavigateToLectureAllClear = { mentorName ->
                onNavigateToAllClear(classId, mentorName)
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
            onNavigateToLecture = { classId ->
                onNavigateToLecture(classId)
            }
        )
    }

    composable(
        route = LectureRoute.LECTURE_ALL_CLEAR,
        arguments = listOf(
            navArgument("classId") { type = NavType.IntType },
            navArgument("mentorName") { type = NavType.StringType }
        )
    ) { backstackEntry ->
        val classId = backstackEntry.arguments?.getInt("classId") ?: 0
        val mentorName = backstackEntry.arguments?.getString("mentorName") ?: ""

        LectureAllClearRoute(
            classId = classId,
            mentorName = mentorName,
            onNavigateToLecture = {
                onNavigateToLecture(classId)
            },
            onNavigateToReward = {
                onNavigateToReward(classId)
            }
        )
    }
}

object LectureRoute {
    fun lectureMainRoute(classId: Int) = "LECTURE/$classId"
    const val LECTURE = "LECTURE/{classId}"

    fun lectureClearRoute(clearedLectureInfo: String) = "LECTURE_CLEAR/$clearedLectureInfo"
    const val LECTURE_CLEAR = "LECTURE_CLEAR/{clearedLectureInfo}"

    fun lectureAllClearRoute(classId: Int, mentorName: String) =
        "LECTURE_ALL_CLEAR/$classId/$mentorName"

    const val LECTURE_ALL_CLEAR = "LECTURE_ALL_CLEAR/{classId}/{mentorName}"
}