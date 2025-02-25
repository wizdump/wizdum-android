package com.teamwizdum.wizdum.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun StartScreen(clickNext: () -> Unit) {
    Surface(
        modifier = Modifier.padding(top = 24.dp, bottom = 80.dp, start = 32.dp, end = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(96.dp))
            Text(
                text = "과거와 미래가\n만나는 곳,\n당신만의 AI 멘토링",
                style = WizdumTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "당신의 하루를 특별하게 만들어줄\n시간을 초월한 멘토와의 대화",
                style = WizdumTheme.typography.body1
            )
            Spacer(modifier = Modifier.weight(1f))
            BasicButton(
                title = "멘토 찾으러 떠나기",
                bodyColor = Color.Green,
                textColor = Color.Black
            ) {
                clickNext()
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun StartScreenPreview() {
    WizdumTheme {
        StartScreen() {}
    }
}