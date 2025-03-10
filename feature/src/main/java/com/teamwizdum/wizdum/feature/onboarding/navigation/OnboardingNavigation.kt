package com.teamwizdum.wizdum.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teamwizdum.wizdum.feature.login.navigation.navigateToLogin
import com.teamwizdum.wizdum.feature.onboarding.InterestSelectionRoute
import com.teamwizdum.wizdum.feature.onboarding.KeywordSelectionRoute
import com.teamwizdum.wizdum.feature.onboarding.MentorDetailRoute
import com.teamwizdum.wizdum.feature.onboarding.MentorMatchRoute
import com.teamwizdum.wizdum.feature.onboarding.LevelSelectionRoute
import com.teamwizdum.wizdum.feature.onboarding.StartScreen
import com.teamwizdum.wizdum.feature.quest.navigation.navigateToLecture


fun NavController.navigateToInterest() {
    navigate(OnboardingRoute.INTEREST)
}

fun NavController.navigateToLevel(interestId: Int) {
    navigate(OnboardingRoute.onboardingLevelRoute(interestId))
}

fun NavController.navigateToKeyword(interestId: Int, levelId: Int) =
    navigate(OnboardingRoute.onboardingKeywordRoute(interestId, levelId))

fun NavController.navigateToMentor(interestId: Int, levelId: Int, categoryId: Int) {
    navigate(OnboardingRoute.onboardingMentorRoute(interestId, levelId, categoryId))
}

fun NavController.navigateToMentorDetail(classId: Int) {
    navigate(OnboardingRoute.onboardingMentorDetailRoute(classId))
}

fun NavGraphBuilder.onboardingScreen(navController: NavHostController) {
    composable(route = OnboardingRoute.START) {
        StartScreen(onNavigateToInterest = {
            navController.navigateToInterest()
        })
    }

    composable(route = OnboardingRoute.INTEREST) {
        InterestSelectionRoute(onNavigateToLevel = { id ->
            navController.navigateToLevel(id)
        })
    }

    composable(
        route = OnboardingRoute.LEVEL,
        arguments = listOf(
            navArgument("interestId") { type = NavType.IntType },
        )
    ) { backstackEntry ->
        val interestId = backstackEntry.arguments?.getInt("interestId") ?: 0

        LevelSelectionRoute(
            onNavigateToKeyword = { levelId ->
                navController.navigateToKeyword(interestId, levelId)
            }
        )
    }

    composable(
        route = OnboardingRoute.KEYWORD,
        arguments = listOf(
            navArgument("interestId") { type = NavType.IntType },
            navArgument("levelId") { type = NavType.IntType },
        )
    ) { backstackEntry ->
        val interestId = backstackEntry.arguments?.getInt("interestId") ?: 0
        val levelId = backstackEntry.arguments?.getInt("interestId") ?: 0

        KeywordSelectionRoute(onNavigateToMentor = { categoryId ->
            navController.navigateToMentor(interestId, levelId, categoryId)
        })
    }

    composable(
        route = OnboardingRoute.MENTOR,
        arguments = listOf(
            navArgument("interestId") { type = NavType.IntType },
            navArgument("levelId") { type = NavType.IntType },
            navArgument("categoryId") { type = NavType.IntType }
        )
    ) { backstackEntry ->
        val interestId = backstackEntry.arguments?.getInt("interestId") ?: 0
        val levelId = backstackEntry.arguments?.getInt("interestId") ?: 0
        val categoryId = backstackEntry.arguments?.getInt("categoryId") ?: 0

        MentorMatchRoute(
            interestId = interestId,
            levelId = levelId,
            categoryId = categoryId
        ) { id ->
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

        MentorDetailRoute(
            classId = classId,
            onNavigateToLogin = {
                navController.navigateToLogin(classId)
            },
            onNavigateToLecture = {
                navController.navigateToLecture(classId)
            }
        )
    }
}

object OnboardingRoute {
    const val START = "START"
    const val INTEREST = "INTEREST"

    fun onboardingLevelRoute(interestId: Int) = "LEVEL/$interestId"
    const val LEVEL = "LEVEL/{interestId}"

    fun onboardingKeywordRoute(interestId: Int, levelId: Int) = "KEYWORD/$interestId/$levelId"
    const val KEYWORD = "KEYWORD/{interestId}/{levelId}"

    fun onboardingMentorRoute(interestId: Int, levelId: Int, categoryId: Int) =
        "MENTOR/$interestId/$levelId/$categoryId"
    const val MENTOR = "MENTOR/{interestId}/{levelId}/{categoryId}"

    fun onboardingMentorDetailRoute(classId: Int) = "MENTOR_DETAIL/$classId"
    const val MENTOR_DETAIL = "MENTOR_DETAIL/{classId}"
}