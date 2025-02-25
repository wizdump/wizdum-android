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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GoalSelectionScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    clickNext: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getQuestion(1)
    }

    val questions = viewModel.questions.collectAsState().value

    Surface(modifier = Modifier.padding(top = 24.dp, bottom = 80.dp, start = 32.dp, end = 32.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(text = "조금 더 구체적으로 알고 싶어요")
            Text(text = "1개 선택 가능")
            Spacer(modifier = Modifier.height(32.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                for (question in questions) {
                    GoalCard(question.content, question.level)
                }
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
private fun GoalCard(content: String, level: String) {
    val levelEnum = Level.fromString(level)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Gray)
            .padding(vertical = 20.dp, horizontal = 12.dp)
    ) {
        Column {
            Text(text = content)
            Row {
                Text(text = "레벨")
                Text(text = levelEnum.rating)
                Text(text = levelEnum.comment)
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

enum class Level(val rating: String, val comment: String) {
    HIGH("⭐⭐⭐", "극강의 몰입 모드 ON!"),
    MEDIUM("⭐⭐", "적당히 강하게!"),
    LOW("⭐", "부담 없이 가볍게!");

    companion object {
        fun fromString(level: String): Level {
            return when (level.uppercase()) {
                "HIGH" -> HIGH
                "MEDIUM" -> MEDIUM
                "LOW" -> LOW
                else -> LOW  // 기본값
            }
        }
    }
}