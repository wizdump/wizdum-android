package com.teamwizdum.wizdum

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.teamwizdum.wizdum.feature.login.LoginViewModel
import com.teamwizdum.wizdum.feature.chat.ChatViewModel
import com.teamwizdum.wizdum.feature.chat.chatScreen
import com.teamwizdum.wizdum.feature.login.loginScreen
import com.teamwizdum.wizdum.feature.onboarding.OnboardingViewModel
import com.teamwizdum.wizdum.feature.onboarding.navigation.onboardingScreen
import com.teamwizdum.wizdum.feature.quest.QuestViewModel
import com.teamwizdum.wizdum.feature.quest.info.ChatRoomInfo
import com.teamwizdum.wizdum.feature.quest.navigation.questScreen
import com.teamwizdum.wizdum.feature.reward.RewardViewModel
import com.teamwizdum.wizdum.feature.reward.rewardScreen

@Composable
fun WizdumNavHost(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    onBoardingViewModel: OnboardingViewModel,
    questViewModel: QuestViewModel,
    chatViewModel: ChatViewModel,
    rewardViewModel: RewardViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ONBOARDING.name
    ) {
        loginScreen(navController, loginViewModel)
        onboardingScreen(navController, onBoardingViewModel)
        questScreen(navController, questViewModel)
        chatScreen(navController, chatViewModel)
        rewardScreen(navController, rewardViewModel)
    }
}