package com.teamwizdum.wizdum.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun MentorMatchScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    clickNext: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.postMentors(1, true)
    }

    val mentors = viewModel.mentors.collectAsState().value

    Surface(modifier = Modifier.padding(top = 24.dp, bottom = 80.dp, start = 32.dp, end = 32.dp)) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .background(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(48.dp))
            Row {
                Text(text = "시간을 초월한\n당신만의 멘토를 찾았어요!", style = WizdumTheme.typography.h2)
            }
            Spacer(modifier = Modifier.height(38.dp))
            MentorCard(mentorInfo = mentors, clickNext = { clickNext() })
//            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//                items(1) {
//                    MentorCard(clickNext = { clickNext() })
//                }
//            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun MentorCard(mentorInfo: MentorsResponse, clickNext: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .height(471.dp)
            .background(color = Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = mentorInfo.name, style = WizdumTheme.typography.h3_semib)
                Text(text = " 멘토님", style = WizdumTheme.typography.body1)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .width(247.dp)
                    .height(247.dp)
                    .background(color = Color.Green)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = mentorInfo.title,
                style = WizdumTheme.typography.h3_semib,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "레벨", style = WizdumTheme.typography.body2)
                Text(text = "⭐⭐⭐", style = WizdumTheme.typography.body2)
                Text(text = "적당히 강하게", style = WizdumTheme.typography.body2)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.Green)
                .align(Alignment.BottomCenter)
                .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .clickable {
                    clickNext()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "강의 미리보기", style = WizdumTheme.typography.body1)
        }
    }
}

@Preview
@Composable
fun MentorCardPreview() {
    WizdumTheme {
        MentorCard(
            mentorInfo = MentorsResponse(
                1,
                "스파르타",
                "스파르타 전사들과 함께하는 나약함은 용납되지 않는다!",
                "강인한 정신력과 철저한 자기 훈련을 통해 목표를 달성하는 스파르타식 도전"
            )
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun MentorMatchScreenPreview() {
    WizdumTheme {
        MentorMatchScreen() {}
    }
}