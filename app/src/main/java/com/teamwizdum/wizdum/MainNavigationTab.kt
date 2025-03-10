package com.teamwizdum.wizdum

import com.teamwizdum.wizdum.feature.home.HomeRoute
import com.teamwizdum.wizdum.feature.mypage.MyPageRoute

enum class MainNavigationTab(
    val selectedResId: Int,
    val unSelectedResId: Int,
    val label: Int,
    val route: String,
) {
    HOME(
        selectedResId = R.drawable.ic_home_selected,
        unSelectedResId = R.drawable.ic_home,
        label = R.string.bottom_navigation_label_home,
        route = HomeRoute.HOME
    ),
    MY_PAGE(
        selectedResId = R.drawable.ic_mypage_selected,
        unSelectedResId = R.drawable.ic_mypage,
        label = R.string.bottom_navigation_label_mypage,
        route = MyPageRoute.MY_PAGE
    );

    companion object {
        operator fun contains(route: String): Boolean {
            return entries.map { it.route }.contains(route)
        }

        fun find(route: String): MainNavigationTab? {
            return entries.find { it.route == route }
        }
    }
}