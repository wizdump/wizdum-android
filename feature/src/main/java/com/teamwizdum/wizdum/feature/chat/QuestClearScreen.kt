package com.teamwizdum.wizdum.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.component.BasicButton
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun QuestClearScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 176.dp, start = 32.dp, end = 32.dp, bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .background(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "1강 클리어!", style = WizdumTheme.typography.h2)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "'결심을 넘어 행동으로'를\n성공적으로 완료했어요.", style = WizdumTheme.typography.body1)
            Spacer(modifier = Modifier.height(48.dp))
            MentorCommentBox()
            Spacer(modifier = Modifier.weight(1f))
            BasicButton(
                title = "계속하기",
                bodyColor = Color.Green,
                textColor = Color.Black,
            ) {
                // TODO : 퀘스트 목록 화면으로 이동
            }
        }
    }
}

@Composable
fun MentorCommentBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Column {
            Text(
                text = "스파르타 멘토님의 격려 한줄",
                style = WizdumTheme.typography.body2_semib
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "좋다. 하지만 이것은 시작에 불과하다.\n다음 단계에서도 너는 더 강해져야 한다.",
                style = WizdumTheme.typography.body1
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun QuestClearScreenPreview() {
    WizdumTheme {
        QuestClearScreen()
    }
}