package com.teamwizdum.wizdum.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.data.model.response.QuestionResponse
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black500
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.Black700
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.onboarding.component.LevelInfo

@Composable
fun QuestionSelectionScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onNavigateNext: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getQuestion(2)
    }

    val questions = viewModel.questions.collectAsState().value

    QuestionContent(questions = questions, onNavigateNext = onNavigateNext)
}

@Composable
private fun QuestionContent(
    questions: List<QuestionResponse>,
    onNavigateNext: () -> Unit,
) {
    val selectedIndex = remember { mutableStateOf(-1) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            BackAppBar()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp, start = 32.dp, end = 32.dp, bottom = 80.dp)
            ) {
                Text(text = "조금 더 구체적으로 알고 싶어요", style = WizdumTheme.typography.h2)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = WizdumTheme.colorScheme.primary)) {
                            append("1개")
                        }
                        append(" 선택 가능")
                    },
                    style = WizdumTheme.typography.body1,
                    color = Black500
                )

                Spacer(modifier = Modifier.height(34.dp))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    questions.forEachIndexed { index, quest ->
                        QuestionCard(
                            content = quest.content,
                            level = quest.level,
                            isSelected = selectedIndex.value == index,
                            onClick = { selectedIndex.value = index }
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

                if (selectedIndex.value != -1)
                    WizdumFilledButton(title = "멘토 추천받기") {
                        onNavigateNext()
                    }
            }
        }
    }
}

@Composable
private fun QuestionCard(
    content: String,
    level: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .border(
                width = 2.dp,
                color = if (isSelected) WizdumTheme.colorScheme.primary else Color.White,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable { onClick() }
            .padding(vertical = 24.dp, horizontal = 16.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = content,
                    style = if (isSelected) WizdumTheme.typography.h3_semib else WizdumTheme.typography.h3,
                    color = if (isSelected) Black700 else Black600
                )
                Spacer(modifier = Modifier.height(8.dp))
                LevelInfo(level = level)
            }
            if (isSelected)
                Image(
                    painter = painterResource(id = R.drawable.ic_checked),
                    contentDescription = "체크 아이콘",
                )
        }
    }
}

@Preview
@Composable
fun QuestionCardPreview() {
    WizdumTheme {
        Column {
            QuestionCard(
                content = "누가 나를 채찍질 해줬으면 좋겠어요",
                level = "HIGH",
                isSelected = true,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(50.dp))
            QuestionCard(
                content = "누가 나를 채찍질 해줬으면 좋겠어요",
                level = "HIGH",
                isSelected = false,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun QuestionSelectionScreenPreview() {
    WizdumTheme {
        val questionList =
            listOf(
                QuestionResponse(content = "이번엔 진짜 끝까지 해내고 싶어요!", level = "HIGH"),
                QuestionResponse(content = "목표를 세웠는데, 시작하기가 어려워요.", level = "MEDIUM"),
                QuestionResponse(content = "누가 나를 채찍질해줬으면 좋겠어요.", level = "LOW")
            )
        QuestionContent(questions = questionList) {}
    }
}