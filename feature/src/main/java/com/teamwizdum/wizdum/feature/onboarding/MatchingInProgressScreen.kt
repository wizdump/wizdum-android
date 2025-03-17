package com.teamwizdum.wizdum.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun MatchingInProgressScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .background(color = Color.White, shape = CircleShape)
                        .border(width = 3.dp, color = Black200, shape = CircleShape),
                )
                Box(
                    modifier = Modifier
                        .padding(start = 57.dp)
                        .width(27.dp)
                        .height(8.dp)
                        .background(color = Green200)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "시간의 문이 열리는 중...",
                style = WizdumTheme.typography.body2,
                color = Black600
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Green200)) {
                        append("운명적인 멘토")
                    }
                    append("와 연결 중이에요!")
                },
                style = WizdumTheme.typography.h3_semib
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun MatchingInProgressScreenPreview() {
    WizdumTheme {
        MatchingInProgressScreen()
    }
}