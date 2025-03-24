package com.teamwizdum.wizdum.feature.onboarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwizdum.wizdum.data.model.response.Lecture
import com.teamwizdum.wizdum.data.model.response.MentorDetailResponse
import com.teamwizdum.wizdum.designsystem.component.appbar.CloseAppBar
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.component.dialog.ErrorDialog
import com.teamwizdum.wizdum.designsystem.component.screen.ErrorScreen
import com.teamwizdum.wizdum.designsystem.component.screen.LoadingScreen
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black500
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.Green50
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.common.base.ErrorState
import com.teamwizdum.wizdum.feature.common.component.LevelInfoCard

// TODO: Status Bar 영역 체크

@Composable
fun MentorDetailRoute(
    viewModel: OnboardingViewModel = hiltViewModel(),
    classId: Int,
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToLecture: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val hasSeenOnboarding = viewModel.hasSeenOnboarding.collectAsState().value
    var errorDialogState by remember { mutableStateOf(false) }
    var retryAction by remember { mutableStateOf({ }) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is OnboardingUiEvent.NavigateToLecture -> {
                    onNavigateToLecture()
                }

                is OnboardingUiEvent.ShowErrorDialog -> {
                    errorDialogState = true
                    retryAction = event.retry
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getMentorDetail(classId)
        viewModel.hasSeenOnboarding()
    }

    when {
        uiState.isLoading || uiState.mentorDetail != MentorDetailResponse() -> {
            MentorDetailScreen(
                uiState = uiState,
                checkOnboarding = hasSeenOnboarding,
                onNavigateBack = onNavigateBack,
                onNavigateToLogin = onNavigateToLogin,
                startLecture = {
                    viewModel.startLecture(classId)
                }
            )
        }

        uiState.handleException is ErrorState.DisplayError -> {
            ErrorScreen(
                retry = ((uiState.handleException as ErrorState.DisplayError).retry)
            )
        }
    }

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
private fun MentorDetailScreen(
    uiState: OnboardingUiState,
    checkOnboarding: Boolean,
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    startLecture: () -> Unit,
) {
    var columnHeightFraction by remember { mutableStateOf(0.76f) }
    val animatedHeight by animateFloatAsState(targetValue = columnHeightFraction, label = "")

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < 0) {
                    columnHeightFraction = (columnHeightFraction + 0.05f).coerceAtMost(1f)
                } else if (available.y > 0) {
                    columnHeightFraction = (columnHeightFraction - 0.05f).coerceAtLeast(0.76f)
                }
                return Offset.Zero
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(uiState.mentorDetail.backgroundImageFilePath)
                .crossfade(true)
                .build(),
            contentDescription = "멘토 배경 이미지",
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(292.dp)
                .background(color = Color(0x80000000))
        )

        CloseAppBar(isDark = true, onClose = onNavigateBack)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(animatedHeight)
                .background(
                    color = Black100,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(top = 32.dp, start = 32.dp)
                .align(Alignment.BottomCenter)
        ) {
            item {
                Column(modifier = Modifier.padding(end = 32.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = uiState.mentorDetail.mentoName,
                            style = WizdumTheme.typography.body1_semib
                        )
                        Text(
                            text = " 멘토님",
                            style = WizdumTheme.typography.body1,
                            color = Black600
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = buildAnnotatedString {
                                append("총 ")
                                withStyle(style = SpanStyle(color = Green200)) {
                                    append("${uiState.mentorDetail.friendWithLectureCount}명")
                                }
                                append(" 함께 수강 중")
                            },
                            style = WizdumTheme.typography.body2,
                            color = Black600
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = uiState.mentorDetail.classTitle, style = WizdumTheme.typography.h2)

                    Spacer(modifier = Modifier.height(8.dp))
                    LevelInfoCard(
                        level = uiState.mentorDetail.itemLevel,
                        levelColor = Black600,
                        subTitleColor = Black600
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Green50, shape = RoundedCornerShape(10.dp))
                            .border(
                                width = 1.dp,
                                color = Green200,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 32.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_speech_balloon),
                            contentDescription = "멘토의 한마디",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "\"${uiState.mentorDetail.wiseSaying}\"",
                            style = WizdumTheme.typography.body2,
                            color = Green200,
                            textAlign = TextAlign.Center,
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "멘토링 스타일", style = WizdumTheme.typography.body1_semib)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.mentorDetail.mentoringStyle,
                        style = WizdumTheme.typography.body1,
                        color = Black600
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "배울점", style = WizdumTheme.typography.body1_semib)
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        uiState.mentorDetail.benefits.forEach { benefit ->
                            Row(verticalAlignment = Alignment.Top) {
                                Box(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .size(2.dp)
                                        .background(color = Black600, shape = CircleShape)
                                )
                                Text(
                                    text = benefit,
                                    style = WizdumTheme.typography.body1,
                                    color = Black600
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "강의 리스트", style = WizdumTheme.typography.body1_semib)
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    items(uiState.mentorDetail.lectures.size) { index ->
                        QuestCard(uiState.mentorDetail.lectures[index])
                    }
                }
                Spacer(modifier = Modifier.height(200.dp))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Transparent, Black600),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 1000f)
                    )
                )
                .align(Alignment.BottomCenter)
        )

        WizdumFilledButton(
            title = "시작하기",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 80.dp,
                    start = 32.dp,
                    end = 32.dp
                )
                .align(Alignment.BottomCenter)
        ) {
            if (checkOnboarding) {
                startLecture()
            } else {
                onNavigateToLogin()
            }
        }

        if (uiState.isLoading) {
            LoadingScreen()
        }
    }
}

@Composable
private fun QuestCard(lecture: Lecture) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(166.dp)
            .clip(shape = RoundedCornerShape(10))
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(lecture.lectureLogoFilePath)
                .crossfade(true)
                .build(),
            contentDescription = "강의 로고 이미지",
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopEnd)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "${lecture.orderSeq}강",
                style = WizdumTheme.typography.body2,
                color = Black500
            )
            Text(text = lecture.title, style = WizdumTheme.typography.body1_semib)
        }
    }
}

@Preview
@Composable
fun QuestCardPreview() {
    QuestCard(Lecture(orderSeq = 1, title = "결심을 넘어\n행동으로"))
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun MentorDetailScreenPreview() {
    WizdumTheme {
        MentorDetailScreen(
            uiState = OnboardingUiState(
                mentorDetail = MentorDetailResponse(
                    mentoName = "스파르타",
                    classTitle = "스파르타 코딩클럽",
                    mentoringStyle = "망설임을 없애고 즉시 실행하는 강철 멘탈 코칭!",
                    friendWithLectureCount = 1,
                    itemLevel = "HIGH",
                    wiseSaying = "강인한 정신력과 철저한 자기 훈련을 통해 목표를 달성하는 스파르타식 도전!",
                    benefits = listOf(
                        "실패를 두려워하지 않는 도전 정신",
                        "목표를 달성하기 위한 전략적 사고와 계획 수립 능력",
                        "계획을 즉각적으로 실행하는 추진력과 끈기"
                    ),
                    lectures = listOf(
                        Lecture(orderSeq = 1, title = "결심을 넘어 행동으로"),
                        Lecture(orderSeq = 1, title = "결심을 넘어 행동으로"),
                        Lecture(orderSeq = 1, title = "결심을 넘어 행동으로")
                    )
                )
            ),
            checkOnboarding = false,
            onNavigateBack = {},
            onNavigateToLogin = {},
            startLecture = {}
        )
    }
}