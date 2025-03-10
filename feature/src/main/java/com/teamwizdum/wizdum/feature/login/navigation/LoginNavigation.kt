package com.teamwizdum.wizdum.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teamwizdum.wizdum.feature.home.HomeRoute
import com.teamwizdum.wizdum.feature.login.LoginRoute
import com.teamwizdum.wizdum.feature.quest.navigation.navigateToLecture

fun NavController.navigateToLogin(classId: Int = -1) {
    navigate(LoginRoute.loginRoute(classId))
}

fun NavGraphBuilder.loginScreen(navController: NavHostController) {
    composable(
        route = LoginRoute.LOGIN,
        arguments = listOf(
            navArgument("classId") {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) { backStackEntry ->
        val classId = backStackEntry.arguments?.getInt("classId") ?: -1
        LoginRoute(
            classId = classId,
            onNavigateToLecture = {
                if (classId != -1) {
                    navController.navigateToLecture(classId)
                }
            },
            onNavigateToHome = { navController.navigate(HomeRoute.HOME) }
        )
    }
}

object LoginRoute {
    fun loginRoute(classId: Int) = "LOGIN/$classId"
    const val LOGIN = "LOGIN/{classId}"
}