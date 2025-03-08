package com.teamwizdum.wizdum.feature.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.myPageScreen(padding: PaddingValues) {
    composable(route = "MYPAGE") {
        MyPageScreen(padding = padding, onNavigateToTerm = {

        })
    }

    composable(route = "TERMS") {
        TermScreen(title = "개인정보처리방침", "https://sites.google.com/view/wizdum-privacy")
    }
}

object MyPageRoute {
    const val MYPAGE = "MYPAGE"
}