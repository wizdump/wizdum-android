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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MentorMatchScreen(clickNext: () -> Unit = {}) {
    Surface(modifier = Modifier.padding(top = 24.dp, bottom = 80.dp, start = 32.dp, end = 32.dp)) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row {
                Text(text = "시간을 초월한\n당신만의 멘토를 찾았어요!")
            }
            Spacer(modifier = Modifier.height(38.dp))
            MentorCard()
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Green)
                .clickable {
                    clickNext()
                }) {
//                Text(
//                    text = "시작하기",
//                    modifier = Modifier
//                        .align(Alignment.Center)
//                        .padding(vertical = 20.dp)
//                        .clickable {
//                            clickNext()
//                        }
//                )
            }
        }
    }
}

@Composable
private fun MentorCard() {
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
            Text(text = "스파르타 멘토님", modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .width(247.dp)
                    .height(247.dp)
                    .background(color = Color.Green)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "작심삼일을 극복하는\n초집중력과 루틴 만들기", modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "레벨")
                Text(text = " 별별별")
                Text(text = " 적당히 강하게")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.Green)
                .align(Alignment.BottomCenter)
                .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "강의 미리보기")
        }
    }
}

@Preview
@Composable
fun MentorCardPreview() {
    MentorCard()
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun MentorMatchScreenPreview() {
    MentorMatchScreen()
}