package com.teamwizdum.wizdum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.chat.ChatViewModel
import com.teamwizdum.wizdum.feature.onboarding.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val onboardingViewModel: OnboardingViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        // TODO: Splash 화면에서 체크 사항 추가
        // splashScreen.setKeepOnScreenCondition { true }

        setContent {
            WizdumTheme {
                val naviController = rememberNavController()

                WizdumNavHost(navController = naviController, viewModel = onboardingViewModel, chatViewModel = chatViewModel)
            }
        }
    }
}