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
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun QuestionSelectionScreen(
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
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .background(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = "조금 더 구체적으로 알고 싶어요", style = WizdumTheme.typography.h2)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "1개 선택 가능", style = WizdumTheme.typography.body1)
            Spacer(modifier = Modifier.height(34.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                for (question in questions) {
                    QuestionCard(question.content, question.level)
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
private fun QuestionCard(content: String, level: String) {
    val levelEnum = Level.fromString(level)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Gray)
            .padding(vertical = 24.dp, horizontal = 16.dp)
    ) {
        Column {
            Text(text = content, style = WizdumTheme.typography.h3)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = "레벨", style = WizdumTheme.typography.body2)
                Text(text = levelEnum.rating, style = WizdumTheme.typography.body2)
                Text(text = levelEnum.comment, style = WizdumTheme.typography.body2)
            }
        }

    }
}

@Preview
@Composable
fun QuestionCardPreview() {
    WizdumTheme {
        QuestionCard(content = "누가 나를 채찍질 해줬으면 좋겠어요", level = "HIGH")
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun QuestionSelectionScreenPreview() {
    WizdumTheme {
        QuestionSelectionScreen() {}
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
                else -> LOW
            }
        }
    }
}