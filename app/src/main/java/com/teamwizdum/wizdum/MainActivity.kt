package com.teamwizdum.wizdum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.login.LoginViewModel
import com.teamwizdum.wizdum.feature.chat.ChatViewModel
import com.teamwizdum.wizdum.feature.home.HomeViewModel
import com.teamwizdum.wizdum.feature.onboarding.OnboardingViewModel
import com.teamwizdum.wizdum.feature.quest.QuestViewModel
import com.teamwizdum.wizdum.feature.reward.RewardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val onboardingViewModel: OnboardingViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val questViewModel: QuestViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val rewardViewModel: RewardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        // TODO: Splash 화면에서 체크 사항 추가
        // splashScreen.setKeepOnScreenCondition { true }

        setContent {
            WizdumTheme {
                val naviController = rememberNavController()

//                WizdumNavHost(
//                    navController = naviController,
//                    loginViewModel = loginViewModel,
//                    onBoardingViewModel = onboardingViewModel,
//                    questViewModel = questViewModel,
//                    chatViewModel = chatViewModel,
//                    rewardViewModel = rewardViewModel
//                )

                MainScreen(
                    navController = naviController,
                    onboardingViewModel = onboardingViewModel,
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}