package com.teamwizdum.wizdum.feature.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun StartScreen(onNavigateNext: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 80.dp, start = 32.dp, end = 32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(96.dp))
            Text(
                text = buildAnnotatedString {
                    append("과거와 미래가\n만나는 곳,\n당신만의 ")
                    withStyle(style = SpanStyle(color = WizdumTheme.colorScheme.primary)) {
                        append("AI 멘토링")
                    }
                },
                style = WizdumTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = buildAnnotatedString {
                    append("당신의 하루를 특별하게 만들어줄\n")
                    withStyle(style = SpanStyle(color = WizdumTheme.colorScheme.primary)) {
                        append("시간을 초월한")
                    }
                    append(" 멘토와의 대화")
                },
                style = WizdumTheme.typography.body1
            )
            Spacer(modifier = Modifier.weight(1f))
            WizdumFilledButton(
                title = "멘토 찾으러 떠나기",
            ) {
                onNavigateNext()
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