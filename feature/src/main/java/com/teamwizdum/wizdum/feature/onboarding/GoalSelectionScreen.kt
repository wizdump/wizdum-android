package com.teamwizdum.wizdum.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GoalSelectionScreen(clickNext: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(text = "오늘은 어떤 하루를 보내고 싶나요?")
            Text(text = "단일선택")
            Column {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "누가 나를 채찍질 해줬으면 좋겠어요")
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "작심삼일 말고 끝까지 해보고 싶어요")
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "목표를 세웠는데 시작하기 어려워요")
                }
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