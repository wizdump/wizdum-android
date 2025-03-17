package com.teamwizdum.wizdum

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.chat.navigation.chatScreen
import com.teamwizdum.wizdum.feature.chat.navigation.navigateToChat
import com.teamwizdum.wizdum.feature.home.navigation.HomeRoute
import com.teamwizdum.wizdum.feature.home.navigation.homeScreen
import com.teamwizdum.wizdum.feature.home.navigation.navigateHome
import com.teamwizdum.wizdum.feature.login.navigation.loginScreen
import com.teamwizdum.wizdum.feature.login.navigation.navigateToLogin
import com.teamwizdum.wizdum.feature.mypage.navigation.myPageScreen
import com.teamwizdum.wizdum.feature.mypage.navigation.navigateToTerm
import com.teamwizdum.wizdum.feature.onboarding.navigation.navigateToInterest
import com.teamwizdum.wizdum.feature.onboarding.navigation.navigateToKeyword
import com.teamwizdum.wizdum.feature.onboarding.navigation.navigateToLevel
import com.teamwizdum.wizdum.feature.onboarding.navigation.navigateToMentor
import com.teamwizdum.wizdum.feature.onboarding.navigation.navigateToMentorDetail
import com.teamwizdum.wizdum.feature.onboarding.navigation.onboardingScreen
import com.teamwizdum.wizdum.feature.quest.navigation.lectureScreen
import com.teamwizdum.wizdum.feature.quest.navigation.navigateToLecture
import com.teamwizdum.wizdum.feature.quest.navigation.navigateToLectureAllClear
import com.teamwizdum.wizdum.feature.quest.navigation.navigateToLectureClear
import com.teamwizdum.wizdum.feature.quest.navigation.navigateToLectureWithHomeRoot
import com.teamwizdum.wizdum.feature.reward.navigation.navigateToReward
import com.teamwizdum.wizdum.feature.reward.navigation.rewardScreen
import timber.log.Timber

@Composable
fun MainScreen(navController: NavHostController) {
    val mainNavigator = MainNavigator(navController)
    val context = LocalContext.current

    navController.addOnDestinationChangedListener { _, destination, _ ->
        navController.backQueue.forEachIndexed { index, entry ->
            Timber.tag("NAVI").d(entry.destination.route)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = HomeRoute.HOME
            ) {
                onboardingScreen(
                    onNavigateBack = navController::popBackStack,
                    onNavigateToInterest = navController::navigateToInterest,
                    onNavigateToLevel = navController::navigateToLevel,
                    onNavigateToKeyword = navController::navigateToKeyword,
                    onNavigateToMentor = navController::navigateToMentor,
                    onNavigateToMentorDetail = navController::navigateToMentorDetail,
                    onNavigateToLogin = navController::navigateToLogin,
                    onNavigateToLecture = navController::navigateToLectureWithHomeRoot
                )
                loginScreen(
                    onNavigateBack = navController::popBackStack,
                    onNavigateToLecture = navController::navigateToLectureWithHomeRoot,
                    onNavigateToHome = navController::navigateHome
                )
                lectureScreen(
                    onNavigateBack = navController::popBackStack,
                    onNavigateToLecture = navController::navigateToLecture,
                    onNavigateToChat = navController::navigateToChat,
                    onNavigateToAllClear = navController::navigateToLectureAllClear,
                    onNavigateToReward = navController::navigateToReward
                )
                chatScreen(
                    onNavigateBack = navController::popBackStack,
                    onNavigateToClear = navController::navigateToLectureClear,
                    onNavigateToAllClear = navController::navigateToLectureAllClear
                )
                rewardScreen(
                    onNavigationBack = navController::popBackStack
                )
                homeScreen(
                    padding = innerPadding,
                    onNavigateToInterest = navController::navigateToInterest,
                    onNavigateToLecture = navController::navigateToLecture,
                )
                myPageScreen(
                    padding = innerPadding,
                    restartMainActivity = {
                        val intent = Intent(context, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        }
                        context.startActivity(intent)
                    },
                    onNavigateToTerm = navController::navigateToTerm
                )
            }
        },
        bottomBar = {
            WizdumBottomBar(
                visible = mainNavigator.shouldShowBottomBar(),
                currentTab = mainNavigator.currentTab,
                onClick = { tab -> mainNavigator.navigate(tab) }
            )
        },

        // Material3에서는 isFloatingActionButtonDocked 옵션 미제공
        // https://stackoverflow.com/questions/73841364/how-to-make-cradled-fab-in-jetpack-compose-material-3
        floatingActionButton = {
            if (mainNavigator.shouldShowBottomBar())
                FloatingActionButton(
                    onClick = { navController.navigateToInterest() },
                    modifier = Modifier
                        .size(50.dp)
                        .offset(y = 40.dp),
                    shape = CircleShape,
                    containerColor = Green200
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_btn_search),
                        contentDescription = "탐색"
                    )
                }
        },
        floatingActionButtonPosition = FabPosition.Center,
    )
}

@Composable
private fun WizdumBottomBar(
    visible: Boolean,
    currentTab: MainNavigationTab?,
    onClick: (MainNavigationTab) -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) },
    ) {
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
                val isHomeSelected = (currentTab == MainNavigationTab.HOME)
                val isMyPageSelected = (currentTab == MainNavigationTab.MY_PAGE)

                NavigationItem(
                    navTab = MainNavigationTab.HOME,
                    isSelected = isHomeSelected,
                    onClick = { onClick(MainNavigationTab.HOME) }
                )
                Spacer(modifier = Modifier.width(46.dp))
                NavigationItem(
                    navTab = MainNavigationTab.MY_PAGE,
                    isSelected = isMyPageSelected,
                    onClick = { onClick(MainNavigationTab.MY_PAGE) }
                )
            }
        }
    }
}

@Composable
private fun NavigationItem(
    navTab: MainNavigationTab,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
            .padding(horizontal = 16.dp)
    ) {
        val iconResId = if (isSelected) navTab.selectedResId else navTab.unSelectedResId
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = stringResource(id = navTab.label)
        )
        Text(
            text = stringResource(id = navTab.label),
            style = if (isSelected) WizdumTheme.typography.body3_semib else WizdumTheme.typography.body3
        )
    }
}
