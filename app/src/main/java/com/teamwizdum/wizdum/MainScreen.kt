package com.teamwizdum.wizdum

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.home.HomeViewModel
import com.teamwizdum.wizdum.feature.home.homeScreen
import com.teamwizdum.wizdum.feature.mypage.myPageScreen
import com.teamwizdum.wizdum.feature.onboarding.OnboardingViewModel
import com.teamwizdum.wizdum.feature.onboarding.navigation.onboardingScreen

@Composable
fun MainScreen(
    navController: NavHostController,
    onboardingViewModel: OnboardingViewModel,
    homeViewModel: HomeViewModel,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            NavHost(navController = navController, startDestination = Routes.HOME.name) {
                homeScreen(padding = innerPadding, homeViewModel = homeViewModel)
                myPageScreen(padding = innerPadding)
                onboardingScreen(
                    navController = navController,
                    viewModel = onboardingViewModel,
                    padding = innerPadding
                )
            }
        },
        bottomBar = {
            WizdumBottomBar(navController = navController)
        },
        // Material3에서는 isFloatingActionButtonDocked 옵션 미제공
        // https://stackoverflow.com/questions/73841364/how-to-make-cradled-fab-in-jetpack-compose-material-3
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("ONBOARDING") },
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .offset(y = 40.dp),
                shape = CircleShape, // TODO: 위치 조정 필요
            ) {
                // TODO: 검색 아이콘 추가
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    )
}

@Composable
private fun WizdumBottomBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable {
                        navController.navigate("HOME")
                    }
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp)
                        .background(color = Color.Black)
                )
                Text(text = "홈", style = WizdumTheme.typography.body3)
            }
            Spacer(modifier = Modifier.width(46.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable {
                        navController.navigate("MYPAGE")
                    }
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp)
                        .background(color = Color.Black)
                )
                Text(text = "마이페이지", style = WizdumTheme.typography.body3)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WizdumBottomBarPreview() {
    WizdumTheme {
        //WizdumBottomBar()
    }
}