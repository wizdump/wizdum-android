package com.teamwizdum.wizdum.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwizdum.wizdum.data.model.response.BeforeAndInProgressLecture
import com.teamwizdum.wizdum.data.model.response.FinishLecture
import com.teamwizdum.wizdum.data.model.response.HomeResponse
import com.teamwizdum.wizdum.designsystem.extension.noRippleClickable
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.onboarding.component.LevelStarRating
import com.teamwizdum.wizdum.feature.quest.component.QuestStatusBadge

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    padding: PaddingValues,
    onNavigateToInterest: () -> Unit,
    onNavigateToLecture: (Int) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getHomeData()
    }

    val homeInfo = viewModel.homeData.collectAsState().value

    HomeScreen(
        padding = padding,
        homeInfo = homeInfo,
        onNavigateToInterest = onNavigateToInterest,
        onNavigateToLecture = onNavigateToLecture
    )
}

@Composable
fun HomeScreen(
    padding: PaddingValues,
    homeInfo: HomeResponse,
    onNavigateToInterest: () -> Unit,
    onNavigateToLecture: (Int) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(
                        top = 48.dp,
                        start = 32.dp,
                        end = 32.dp,
                        bottom = 24.dp
                    )
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = buildAnnotatedString {
                        append("${homeInfo.username}님은 지금까지\n총 ")
                        withStyle(style = SpanStyle(color = WizdumTheme.colorScheme.primary)) {
                            append("${homeInfo.myWizCount}")
                        }
                        append("개의 Wiz를\n습득하셨어요")
                    },
                    style = WizdumTheme.typography.h2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        append("\uD83D\uDCAA 총 ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append("${homeInfo.friendWithLectureCount}명")
                        }
                        append(" 의 친구들이 같이 수강중이에요")
                    },
                    style = WizdumTheme.typography.body2
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Black100)
            ) {
                item {
                    Row(
                        modifier = Modifier.padding(top = 32.dp, start = 32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "지금 듣고 있는 클래스", style = WizdumTheme.typography.body1_semib)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${homeInfo.beforeAndInProgressLectures.size}",
                            style = WizdumTheme.typography.body1
                        )
                    }

                    if (homeInfo.beforeAndInProgressLectures.isEmpty()) {
                        TodayWizCard(
                            onNavigateToInterest = onNavigateToInterest
                        )
                    } else {
                        LazyRow(
                            modifier = Modifier.padding(top = 16.dp, start = 32.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(homeInfo.beforeAndInProgressLectures) { lecture ->
                                InProgressWizCard(
                                    inProgressLecture = lecture,
                                    onNavigateToLecture = { onNavigateToLecture(lecture.classId) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        modifier = Modifier.padding(start = 32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "습득한 Wiz", style = WizdumTheme.typography.body1_semib)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${homeInfo.finishLectures.size}",
                            style = WizdumTheme.typography.body1_semib
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    if (homeInfo.finishLectures.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(51.dp)
                                .padding(horizontal = 32.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "수강 완료된 강의가 아직 없어요",
                                style = WizdumTheme.typography.body2,
                                modifier = Modifier.align(
                                    Alignment.Center
                                )
                            )
                        }
                    } else {
                        homeInfo.finishLectures.forEach { lecture ->
                            CollectionWizCard(
                                finishedLecture = lecture,
                                onNavigateToLecture = { onNavigateToLecture(lecture.classId) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TodayWizCard(onNavigateToInterest: () -> Unit) {
    Box(
        modifier = Modifier
            .width(202.dp)
            .height(194.dp)
            .padding(start = 32.dp)
    ) {
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(166.dp)
                .background(color = Green200, shape = RoundedCornerShape(20.dp))
                .padding(24.dp)
                .align(Alignment.BottomStart)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ) {
                Text(text = "오늘의", color = Color.White, style = WizdumTheme.typography.body3)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "나에게 꼭 맞는\n멘토 찾기",
                    color = Color.White,
                    style = WizdumTheme.typography.body1_semib
                )
            }
        }

        Box(
            modifier = Modifier
                .width(88.dp)
                .height(88.dp)
                .background(color = Black100, shape = CircleShape)
                .align(Alignment.TopEnd)
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(color = Green200, shape = CircleShape)
                    .align(Alignment.Center)
                    .noRippleClickable {
                        onNavigateToInterest()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_btn_search),
                    contentDescription = "탐색"
                )
            }
        }
    }
}

@Composable
fun InProgressWizCard(
    inProgressLecture: BeforeAndInProgressLecture,
    onNavigateToLecture: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .height(230.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .clickable {
                onNavigateToLecture()
            }
            .padding(16.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(inProgressLecture.logoFilePath)
                .crossfade(true)
                .build(),
            contentDescription = "멘토 상징 로고",
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(24.dp))
        QuestStatusBadge(status = inProgressLecture.lectureStatus)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${inProgressLecture.mentoName} 멘토님", style = WizdumTheme.typography.body3)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = inProgressLecture.classTitle,
            style = WizdumTheme.typography.body1_semib
        )
        Spacer(modifier = Modifier.height(8.dp))
        LevelStarRating(level = inProgressLecture.itemLevel)
    }
}

@Composable
fun CollectionWizCard(
    finishedLecture: FinishLecture,
    onNavigateToLecture: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .clickable {
                onNavigateToLecture()
            }
    ) {
        Box(
            modifier = Modifier
                .padding(end = 20.dp)
                .width(29.dp)
                .height(32.dp)
                .background(
                    color = Green200,
                    shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                )
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_certificated),
                contentDescription = "인증"
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append(finishedLecture.mentoName)
                    }
                    append(" 멘토님")
                },
                style = WizdumTheme.typography.body3
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = finishedLecture.classTitle,
                style = WizdumTheme.typography.body2_semib
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_checked),
                    contentDescription = "완료",
                    modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = finishedLecture.completedAt, style = WizdumTheme.typography.body3)
            }
        }
    }
}

@Preview
@Composable
fun TodayWizCardPreview() {
    WizdumTheme {
        TodayWizCard {}
    }
}

@Preview
@Composable
fun InProgressWizCardPreview() {
    WizdumTheme {
        InProgressWizCard(
            BeforeAndInProgressLecture(
                mentoId = 1,
                mentoName = "스파르타",
                logoFilePath = "",
                classId = 0,
                classTitle = "작심삼일을 극복하는 초집중력과 루틴 만들기",
                lectureId = 1,
                lectureStatus = "WAIT",
                itemLevel = "MEDIUM"
            )
        ) {}
    }
}

@Preview
@Composable
fun CollectionWizCardPreview() {
    WizdumTheme {
        CollectionWizCard(
            FinishLecture(
                mentoId = 2,
                mentoName = "윈스턴 처칠",
                classTitle = "작심삼일을 극복하는 초집중력과 루틴 만들기",
                classId = 0,
                lectureId = 2,
                completedAt = "2023-10-27 10:00:00"
            )
        ) {}
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun HomeScreenPreview() {
    val homeResponse = HomeResponse(
        username = "유니",
        myWizCount = 2,
        friendWithLectureCount = 365,
        beforeAndInProgressLectures = listOf(
            BeforeAndInProgressLecture(
                mentoId = 1,
                mentoName = "스파르타",
                logoFilePath = "",
                classId = 0,
                classTitle = "작심삼일을 극복하는 초집중력과 루틴 만들기",
                lectureId = 1,
                lectureStatus = "WAIT",
                itemLevel = "MEDIUM"
            )
        ),
        finishLectures = listOf(
            FinishLecture(
                mentoId = 2,
                mentoName = "윈스턴 처칠",
                classTitle = "작심삼일을 극복하는 초집중력과 루틴 만들기",
                classId = 0,
                lectureId = 2,
                completedAt = "2023-10-27 10:00:00"
            )
        )
    )
    WizdumTheme {
        HomeScreen(
            padding = PaddingValues(),
            homeInfo = homeResponse,
            onNavigateToInterest = {},
            onNavigateToLecture = {}
        )
    }
}