package com.teamwizdum.wizdum.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.homeScreen(padding: PaddingValues, homeViewModel: HomeViewModel) {
    composable(route = "HOME") {
        HomeScreen(padding = padding, viewModel = homeViewModel)
    }
}