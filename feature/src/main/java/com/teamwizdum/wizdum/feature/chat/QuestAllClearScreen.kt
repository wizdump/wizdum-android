package com.teamwizdum.wizdum.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun QuestAllClearScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 176.dp, start = 32.dp, end = 32.dp, bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "스파르타 멘토님과", style = WizdumTheme.typography.body1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "모든 강의 클리어!", style = WizdumTheme.typography.h2)
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .width(360.dp)
                    .height(300.dp)
                    .background(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(48.dp))
            Spacer(modifier = Modifier.weight(1f))
//            BasicButton(
//                title = "리워드 받기",
//                bodyColor = Color.Green,
//                textColor = Color.Black,
//            ) {
//                // TODO : 리워드 화면으로 이동
//            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun QuestAllClearScreenPreview() {
    WizdumTheme {
        QuestAllClearScreen()
    }
}