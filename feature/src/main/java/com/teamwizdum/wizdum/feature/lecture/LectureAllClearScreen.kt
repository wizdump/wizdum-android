package com.teamwizdum.wizdum.feature.lecture

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.designsystem.component.appbar.CloseAppBar
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.component.dialog.ErrorDialog
import com.teamwizdum.wizdum.designsystem.component.screen.LoadingScreen
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R

@Composable
fun LectureAllClearRoute(
    viewModel: LectureViewModel = hiltViewModel(),
    classId: Int,
    mentorName: String,
    onNavigateBack: () -> Unit,
    onNavigateToReward: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    var errorDialogState by remember { mutableStateOf(false) }
    var retryAction by remember { mutableStateOf({ }) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is LectureUiEvent.NavigateToReward -> {
                    onNavigateToReward()
                }

                is LectureUiEvent.ShowErrorDialog -> {
                    errorDialogState = true
                    retryAction = event.retry
                }
            }
        }
    }

    LectureAllClearScreen(
        uiState = uiState,
        mentorName = mentorName,
        onNavigateBack = onNavigateBack,
        onNavigateToReward = {
            viewModel.postReward(classId)
        }
    )

    ErrorDialog(
        dialogState = errorDialogState,
        onDismissRequest = {
            errorDialogState = false
        },
        retry = {
            errorDialogState = false
            retryAction()
        }
    )
}

@Composable
private fun LectureAllClearScreen(
    uiState: LectureUiState,
    mentorName: String,
    onNavigateBack: () -> Unit,
    onNavigateToReward: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        CloseAppBar {
            onNavigateBack()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp, start = 32.dp, end = 32.dp, bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "$mentorName 멘토님과", style = WizdumTheme.typography.body1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "모든 강의 클리어!", style = WizdumTheme.typography.h2)
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.height(311.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_checked),
                        contentDescription = "체크",
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.img_clapping_hands),
                        contentDescription = "모든 강의 완료",
                        modifier = Modifier
                            .width(112.dp)
                            .height(112.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            WizdumFilledButton(
                modifier = Modifier.fillMaxWidth(),
                title = "리워드 받기",
                onClick = {
                    onNavigateToReward()
                }
            )
        }

        if (uiState.isLoading) {
            LoadingScreen()
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun LectureAllClearScreenPreview() {
    WizdumTheme {
        LectureAllClearScreen(
            uiState = LectureUiState(),
            mentorName = "스파르타",
            onNavigateBack = {},
            onNavigateToReward = {}
        )
    }
}