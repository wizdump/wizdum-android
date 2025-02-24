package com.teamwizdum.wizdum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.teamwizdum.wizdum.designsystem.theme.WizdumandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        // TODO: Splash 화면에서 체크 사항 추가
        // splashScreen.setKeepOnScreenCondition { true }

        setContent {
            WizdumandroidTheme {
                val naviController = rememberNavController()

                WizdumNavHost(navController = naviController)
            }
        }
    }
}