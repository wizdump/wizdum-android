package com.teamwizdum.wizdum.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teamwizdum.wizdum.feature.login.LoginRoute

fun NavController.navigateToLogin(classId: Int = -1) {
    navigate(LoginRoute.loginRoute(classId))
}

fun NavGraphBuilder.loginScreen(
    onNavigateBack: () -> Unit,
    onNavigateToLecture: (Int) -> Unit,
    onNavigateToHome: () -> Unit,
) {
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
            onNavigateBack = onNavigateBack,
            onNavigateToLecture = {
                if (classId != -1) {
                    onNavigateToLecture(classId)
                }
            },
            onNavigateToHome = onNavigateToHome
        )
    }
}

object LoginRoute {
    fun loginRoute(classId: Int) = "LOGIN/$classId"
    const val LOGIN = "LOGIN/{classId}"
}