package com.teamwizdum.wizdum.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.data.model.response.BeforeAndInProgressLecture
import com.teamwizdum.wizdum.data.model.response.FinishLecture
import com.teamwizdum.wizdum.data.model.response.HomeResponse
import com.teamwizdum.wizdum.designsystem.component.badge.TextWithIconBadge
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.onboarding.component.LevelStarRating
import com.teamwizdum.wizdum.feature.quest.component.QuestStatusBadge

@Composable
fun HomeScreen(padding: PaddingValues, viewModel: HomeViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getHomeData()
    }

    val homeInfo = viewModel.homeData.collectAsState().value

    HomeContent(padding, homeInfo)
}

@Composable
private fun HomeContent(padding: PaddingValues, homeInfo: HomeResponse) {
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
                    text = "유니님은 지금까지\n총 ${homeInfo.myWizCount}개의 Wiz를\n습득하셨어요",
                    style = WizdumTheme.typography.h2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "총 ${homeInfo.friendWithLectureCount}명 의 친구들이 같이 수강중이에요",
                    style = WizdumTheme.typography.body2
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Black100)
                    .padding(32.dp)
            ) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "지금 듣고 있는 클래스", style = WizdumTheme.typography.body1_semib)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${homeInfo.beforeAndInProgressLectures.size}",
                            style = WizdumTheme.typography.body1
                        )
                    }

                    if (homeInfo.beforeAndInProgressLectures.isEmpty()) {
                        TodayWizCard()
                    } else {
                        LazyRow(modifier = Modifier.padding(top = 16.dp)) {
                            items(homeInfo.beforeAndInProgressLectures) { quest ->
                                InProgressWizCard(quest)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
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
                        CollectionWizCard()
                    }
                }
            }
        }
    }
}

@Composable
fun TodayWizCard() {
    Box(
        modifier = Modifier
            .width(156.dp)
            .height(194.dp)
    ) {
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(166.dp)
                .background(color = Color.Black, shape = RoundedCornerShape(20.dp))
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
                    .width(54.dp)
                    .height(54.dp)
                    .background(color = Color.Black, shape = CircleShape)
                    .align(Alignment.Center)
            ) {

            }
        }
    }
}

@Composable
fun InProgressWizCard(inProgressLecture: BeforeAndInProgressLecture) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .height(230.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .background(color = Color.Green)
                .align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(24.dp))
        QuestStatusBadge(status = inProgressLecture.lectureStatus)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${inProgressLecture.mentoName} 멘토님", style = WizdumTheme.typography.body3)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "작심삼일을 극복하는 초집중력과 루틴 만들기",
            style = WizdumTheme.typography.body1_semib
        )
        Spacer(modifier = Modifier.height(8.dp))
        LevelStarRating(level = inProgressLecture.itemLevel)
    }
}

@Composable
fun CollectionWizCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = Modifier
                .padding(end = 20.dp)
                .width(29.dp)
                .height(32.dp)
                .background(
                    color = Color.Black,
                    shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                )
                .align(Alignment.TopEnd)
        ) {
            // TODO: 아이콘 추가
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "스파르타", style = WizdumTheme.typography.body3_semib)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "멘토님", style = WizdumTheme.typography.body3)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "작심삼일을 극복하는 초집중력과 루틴 만들기", style = WizdumTheme.typography.body2_semib)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "2025년 2월 23일 완료", style = WizdumTheme.typography.body3)
        }
    }
}

@Preview
@Composable
fun TodayWizCardPreview() {
    WizdumTheme {
        TodayWizCard()
    }
}

//@Preview
//@Composable
//fun InProgressWizCardPreview() {
//    WizdumTheme {
//        InProgressWizCard()
//    }
//}

@Preview
@Composable
fun CollectionWizCardPreview() {
    WizdumTheme {
        CollectionWizCard()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun HomeScreenPreview() {
    val homeResponse = HomeResponse(
        myWizCount = 2,
        friendWithLectureCount = 365,
        beforeAndInProgressLectures = listOf(
            BeforeAndInProgressLecture(
                mentoId = 1,
                mentoName = "스파르타",
                lectureId = 1,
                lectureStatus = "WAIT",
                itemLevel = "MEDIUM"
            )
        ),
        finishLectures = listOf(
            FinishLecture(
                mentoId = 2,
                mentoName = "윈스턴 처칠",
                lectureId = 2,
                completedAt = "2023-10-27 10:00:00"
            )
        )
    )
    WizdumTheme {
        HomeContent(padding = PaddingValues(), homeResponse)
    }
}