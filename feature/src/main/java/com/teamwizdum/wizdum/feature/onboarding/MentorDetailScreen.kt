package com.teamwizdum.wizdum.feature.onboarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.data.model.response.MentorDetailResponse
import com.teamwizdum.wizdum.designsystem.component.appbar.CloseAppBar
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun MentorDetailScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onNavigateNext: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getMentorDetail(1)
    }

    val mentorInfo = viewModel.mentorInfo.collectAsState().value

    MentorDetailContent(mentorInfo = mentorInfo, onNavigateNext = onNavigateNext)
}

@Composable
private fun MentorDetailContent(mentorInfo: MentorDetailResponse, onNavigateNext: () -> Unit) {
    var columnHeightFraction by remember { mutableStateOf(0.76f) }
    val animatedHeight by animateFloatAsState(targetValue = columnHeightFraction, label = "")

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < 0) {
                    columnHeightFraction = (columnHeightFraction + 0.05f).coerceAtMost(0.9f)
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .background(color = Color.White)
        )

        CloseAppBar()

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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Text(text = "스파르타", style = WizdumTheme.typography.body1_semib)
                            Text(
                                text = " 멘토님",
                                style = WizdumTheme.typography.body1,
                                color = Black600
                            )
                        }
                        Text(
                            text = "소요시간 16분",
                            style = WizdumTheme.typography.body1
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "작심삼일을 극복하는\n초집중력과 루틴 만들기", style = WizdumTheme.typography.h2)
                    Spacer(modifier = Modifier.height(8.dp))
                    // LevelInfo(level = "")
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "결심은 약하다. 행동만이 너를 강하게 만든다!",
                        style = WizdumTheme.typography.body2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                            .padding(horizontal = 32.dp, vertical = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "멘토링 스타일", style = WizdumTheme.typography.body1_semib)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "망설임을 없애고 즉시 실행하는 강철 멘탈 코칭!", style = WizdumTheme.typography.body1)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "배울점", style = WizdumTheme.typography.body1_semib)
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(text = "- 작심삼일을 극복하는 스파르타식 마인드셋", style = WizdumTheme.typography.body1)
                        Text(
                            text = "- 목표를 습관으로 만들기 위한 초집중 루틴",
                            style = WizdumTheme.typography.body1
                        )
                        Text(
                            text = "- 결심에서 행동으로 즉시 전환하는 '2초 실행법'",
                            style = WizdumTheme.typography.body1
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "강의 리스트", style = WizdumTheme.typography.body1_semib)
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    items(3) {
                        QuestCard()
                    }
                }
                Spacer(modifier = Modifier.height(200.dp))

            }

        }

        WizdumFilledButton(
            title = "시작하기",
            backgroundColor = Color.Green,
            textColor = Color.Black,
            modifier = Modifier
                .padding(
                    bottom = 80.dp,
                    start = 32.dp,
                    end = 32.dp
                )
                .align(Alignment.BottomCenter)
        ) {
            onNavigateNext()
        }
    }
}

@Composable
private fun QuestCard() {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(166.dp)
            .clip(shape = RoundedCornerShape(10))
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .background(color = Color.Green)
                .align(Alignment.TopEnd)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(text = "1강", style = WizdumTheme.typography.body2)
            Text(text = "결심을 넘어\n행동으로", style = WizdumTheme.typography.body1_semib)
        }
    }
}

@Preview
@Composable
fun QuestCardPreview() {
    QuestCard()
}

//@Preview(showBackground = true, widthDp = 360, heightDp = 800)
//@Composable
//fun MentorDetailScreenPreview() {
//    WizdumTheme {
//        MentorDetailContent(mentorInfo = ) {}
//    }
//}