package com.teamwizdum.wizdum

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.teamwizdum.wizdum.feature.home.navigation.HomeRoute
import com.teamwizdum.wizdum.feature.mypage.navigation.MyPageRoute

class MainNavigator(private val navController: NavHostController) {

    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTab: MainNavigationTab?
        @Composable get() = currentDestination
            ?.route
            ?.let(MainNavigationTab::find)

    fun navigate(tab: MainNavigationTab) {
        val navOption = navOptions {
            popUpTo(HomeRoute.HOME) // 홈화면 루트 유지
            launchSingleTop = true // 중복 이동 방지
        }
        when (tab) {
            MainNavigationTab.HOME -> navController.navigate(HomeRoute.HOME, navOption)
            MainNavigationTab.MY_PAGE -> navController.navigate(MyPageRoute.MY_PAGE, navOption)
        }
    }

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainNavigationTab
    }
}