package com.teamwizdum.wizdum.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teamwizdum.wizdum.feature.onboarding.KeywordSelectionRoute
import com.teamwizdum.wizdum.feature.onboarding.MentorDetailScreen
import com.teamwizdum.wizdum.feature.onboarding.MentorMatchRoute
import com.teamwizdum.wizdum.feature.onboarding.QuestionSelectionScreen
import com.teamwizdum.wizdum.feature.onboarding.StartScreen

fun NavController.navigateToKeyword() =
    navigate(OnboardingRoute.KEYWORD)

fun NavController.navigateToQuestion(keywordId: Int) {
    navigate(OnboardingRoute.onboardingQuestionRoute(keywordId))
}

fun NavController.navigateToMentor(categoryId: Int) {
    navigate(OnboardingRoute.onboardingMentorRoute(categoryId))
}

fun NavController.navigateToMentorDetail(classId: Int) {
    navigate(OnboardingRoute.onboardingMentorDetailRoute(classId))
}

fun NavGraphBuilder.onboardingScreen(navController: NavHostController) {
    composable(route = OnboardingRoute.START) {
        StartScreen(onNavigateToKeyword = {
            navController.navigateToKeyword()
        })
    }

    composable(route = OnboardingRoute.KEYWORD) {
        KeywordSelectionRoute(onNavigateToQuestion = { id ->
            navController.navigateToQuestion(id)
        })
    }

    composable(
        route = OnboardingRoute.QUESTION,
        arguments = listOf(
            navArgument("keywordId") { type = NavType.IntType },
        )
    ) { backstackEntry ->
        val keywordId = backstackEntry.arguments?.getInt("keywordId") ?: 0

        QuestionSelectionScreen(
            keywordId = keywordId,
            onNavigateToMentor = { id ->
                navController.navigateToMentor(id)
            }
        )
    }

    composable(
        route = OnboardingRoute.MENTOR,
        arguments = listOf(
            navArgument("categoryId") { type = NavType.IntType }
        )
    ) { backstackEntry ->
        val categoryId = backstackEntry.arguments?.getInt("categoryId") ?: 0

        MentorMatchRoute(categoryId = categoryId) { id ->
            navController.navigateToMentorDetail(id)
        }
    }


    composable(
        route = OnboardingRoute.MENTOR_DETAIL,
        arguments = listOf(
            navArgument("classId") { type = NavType.IntType }
        )
    ) { backstackEntry ->
        val classId = backstackEntry.arguments?.getInt("classId") ?: 0

        MentorDetailScreen(classId = classId) {
            navController.navigate(route = "LOGIN")
        }
    }
}

object OnboardingRoute {
    const val START = "START"
    const val KEYWORD = "KEYWORD"

    fun onboardingQuestionRoute(keywordId: Int) = "QUESTION/$keywordId"
    const val QUESTION = "QUESTION/{keywordId}"

    fun onboardingMentorRoute(categoryId: Int) = "MENTOR/$categoryId"
    const val MENTOR = "MENTOR/{categoryId}"

    fun onboardingMentorDetailRoute(classId: Int) = "MENTOR_DETAIL/$classId"
    const val MENTOR_DETAIL = "MENTOR_DETAIL/{classId}"
}