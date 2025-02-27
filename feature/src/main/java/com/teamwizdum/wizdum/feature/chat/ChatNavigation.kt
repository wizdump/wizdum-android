package com.teamwizdum.wizdum.feature.chat

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.chatScreen(viewModel: ChatViewModel) {
    composable(route= "CHAT") {
        ChatScreen(viewModel = viewModel)
    }
}