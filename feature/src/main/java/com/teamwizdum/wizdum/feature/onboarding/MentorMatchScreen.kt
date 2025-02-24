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
import androidx.compose.ui.unit.dp

@Composable
fun MentorMatchScreen(clickNext: () -> Unit = {}) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(text = "당신에게 딱 맞는 멘토가\n시간을 초월해서 달려 왔어요!")
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(Color.Green)) {
                Text(text = "스파르타 멘토님")
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Green)
                .clickable {
                    clickNext()
                }) {
                Text(
                    text = "시작하기",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 20.dp)
                        .clickable {
                            clickNext()
                        }
                )
            }
        }
    }

}