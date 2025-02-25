package com.teamwizdum.wizdum.feature.onboarding

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
import androidx.compose.foundation.layout.padding
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
fun GoalSelectionScreen(clickNext: () -> Unit) {
    Surface(modifier = Modifier.padding(top = 24.dp, bottom = 80.dp, start = 32.dp, end = 32.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(text = "조금 더 구체적으로 알고 싶어요")
            Text(text = "1개 선택 가능")
            Spacer(modifier = Modifier.height(32.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                GoalCard()
                GoalCard()
                GoalCard()
            }
            Spacer(modifier = Modifier.height(32.dp))
            Box {
                Text(text = "다른 고민 보기")
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Green)
                .clickable {
                    clickNext()
                }) {
                Text(
                    text = "멘토 추천받기", modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 20.dp)
                )
            }
        }
    }
}

@Composable
private fun GoalCard(title: String = "") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Gray)
            .padding(vertical = 20.dp, horizontal = 12.dp)
    ) {
        Column {
            Text(text = "누가 나를 채찍질 해줬으면 좋겠어요")
            Row {
                Text(text = "레벨")
                Text(text = "⭐️⭐⭐")
                Text(text = "극강의 몰입 모드 ON!")
            }
        }

    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun GoalSelectionScreenPreview() {
    GoalSelectionScreen {

    }
}