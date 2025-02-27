package com.teamwizdum.wizdum.feature.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.teamwizdum.wizdum.feature.home.HomeScreen

fun NavGraphBuilder.myPageScreen(padding: PaddingValues) {
    composable(route = "MYPAGE") {
        MyPageScreen(padding = padding)
    }
}