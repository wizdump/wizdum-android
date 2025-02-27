package com.teamwizdum.wizdum.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun HomeScreen(padding: PaddingValues) {
    Surface(modifier = Modifier.fillMaxSize().padding(padding)) {
        Column {
            Column(
                modifier = Modifier.padding(
                    top = 48.dp,
                    start = 32.dp,
                    end = 32.dp,
                    bottom = 24.dp
                )
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                Text(text = "유니님은 지금까지\n총 1개의 Wiz를\n습득하셨어요", style = WizdumTheme.typography.h2)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "총 공부시간", style = WizdumTheme.typography.body2)
                    Text(text = "16분", style = WizdumTheme.typography.body2_semib)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray)
                    .padding(32.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "오늘의 위즈 Wiz", style = WizdumTheme.typography.body1_semib)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "0", style = WizdumTheme.typography.body1)
                }
                TodayWizCard()
                Spacer(modifier = Modifier.height(40.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "습득한 Wiz", style = WizdumTheme.typography.body1_semib)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "1", style = WizdumTheme.typography.body1_semib)
                }
                Spacer(modifier = Modifier.height(16.dp))
                CollectionWizCard()
            }
        }
    }
}

@Composable
fun TodayWizCard() {
    Box(
        modifier = Modifier
            .width(156.dp)
            .height(194.dp)
    ) {
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(166.dp)
                .background(color = Color.Black, shape = RoundedCornerShape(20.dp))
                .padding(24.dp)
                .align(Alignment.BottomStart)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ) {
                Text(text = "오늘의", color = Color.White, style = WizdumTheme.typography.body3)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "나에게 꼭 맞는\n멘토 찾기",
                    color = Color.White,
                    style = WizdumTheme.typography.body1_semib
                )
            }
        }

        Box(
            modifier = Modifier
                .width(88.dp)
                .height(88.dp)
                .background(color = Color.Gray, shape = CircleShape)
                .align(Alignment.TopEnd)
        ) {
            Box(
                modifier = Modifier
                    .width(54.dp)
                    .height(54.dp)
                    .background(color = Color.Black, shape = CircleShape)
                    .align(Alignment.Center)
            ) {

            }
        }
    }
}

@Composable
fun CollectionWizCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = Modifier
                .padding(end = 20.dp)
                .width(29.dp)
                .height(32.dp)
                .background(
                    color = Color.Black,
                    shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                )
                .align(Alignment.TopEnd)
        ) {
            // TODO: 아이콘 추가
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "스파르타", style = WizdumTheme.typography.body3_semib)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "멘토님", style = WizdumTheme.typography.body3)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "작심삼일을 극복하는 초집중력과 루틴 만들기", style = WizdumTheme.typography.body2_semib)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "2025년 2월 23일 완료", style = WizdumTheme.typography.body3)
        }
    }
}

@Preview
@Composable
fun TodayWizCardPreview() {
    WizdumTheme {
        TodayWizCard()
    }
}

@Preview
@Composable
fun CollectionWizCardPreview() {
    WizdumTheme {
        CollectionWizCard()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun HomeScreenPreview() {
    WizdumTheme {
        HomeScreen(padding = PaddingValues())
    }
}