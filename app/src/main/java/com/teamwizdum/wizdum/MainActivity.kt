package com.teamwizdum.wizdum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.teamwizdum.wizdum.feature.login.navigation.navigateToLogin
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.home.HomeRoute
import com.teamwizdum.wizdum.feature.onboarding.navigation.OnboardingRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        setContent {
            WizdumTheme {
                val navController = rememberNavController()
                val mainState by viewModel.mainState.collectAsState()

                splashScreen.setKeepOnScreenCondition { mainState == MainState.None }

                LaunchedEffect(mainState) {
                    when (mainState) {
                        MainState.Onboarding -> navController.navigate(OnboardingRoute.START) { popUpTo(0) }
                        MainState.Login -> navController.navigateToLogin(navOptions = navOptions {
                            run { popUpTo(0) }
                        })
                        MainState.Home -> navController.navigate(HomeRoute.HOME) { popUpTo(0) }
                        else -> {}
                    }
                }

                MainScreen(navController = navController)
            }
        }
    }
}