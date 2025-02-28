package com.teamwizdum.wizdum.feature.reward

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun RewardScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 176.dp, start = 32.dp, end = 32.dp, bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "스파르타 멘토님이 유니님에게", style = WizdumTheme.typography.body1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "리워드를 수여하셨습니다!", style = WizdumTheme.typography.h2)
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .width(360.dp)
                    .height(300.dp)
                    .background(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(48.dp))
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .background(color = Color.Black)
                )
                Spacer(modifier = Modifier.width(16.dp))
                WizdumFilledButton(
                    title = "나의 승리 공유하기",
                    backgroundColor = Color.Green,
                    textColor = Color.Black,
                ) {
                    // TODO : 공유로 넘어가면 창 종료, 복귀 시 메인 화면으로 이동
                }
            }

        }
    }
}

@Preview
@Composable
fun RewardScreenPreview() {
    WizdumTheme {
        RewardScreen()
    }
}