package com.teamwizdum.wizdum.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teamwizdum.wizdum.feature.login.navigation.navigateToLogin
import com.teamwizdum.wizdum.feature.mypage.MyPageRoute
import com.teamwizdum.wizdum.feature.mypage.TermScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun NavController.navigateToTerm(title: String, webUrl: String) {
    val encodeUrl = URLEncoder.encode(webUrl, StandardCharsets.UTF_8.toString())
    navigate(MyPageRoute.myPageTermRoute(title, encodeUrl))
}

fun NavGraphBuilder.myPageScreen(padding: PaddingValues, navController: NavHostController) {

    composable(route = MyPageRoute.MY_PAGE) {
        MyPageRoute(
            padding = padding,
            onNavigateToLogin = {
                navController.navigateToLogin()
            },
            onNavigateToTerm = { title, webUrl ->
                navController.navigateToTerm(title, webUrl)
            }
        )
    }

    composable(
        route = MyPageRoute.TERM,
        arguments = listOf(
            navArgument("title") { type = NavType.StringType },
            navArgument("webUrl") { type = NavType.StringType }
        )
    ) { backstackEntry ->
        val title = backstackEntry.arguments?.getString("title") ?: ""
        val webUrl = backstackEntry.arguments?.getString("webUrl") ?: ""

        TermScreen(title = title, webUrl = webUrl)
    }
}

object MyPageRoute {
    const val MY_PAGE = "MY_PAGE"

    fun myPageTermRoute(title: String, webUrl: String) = "TERM/$title/$webUrl"
    const val TERM = "TERM/{title}/{webUrl}"
}