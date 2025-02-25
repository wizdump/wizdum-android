package com.teamwizdum.wizdum.feature.onboarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MentorDetailScreen(clickNext: () -> Unit = {}) {
    var columnHeightFraction by remember { mutableStateOf(0.76f) }
    val animatedHeight by animateFloatAsState(targetValue = columnHeightFraction, label = "")

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < 0) {
                    columnHeightFraction = (columnHeightFraction + 0.05f).coerceAtMost(0.9f)
                } else if (available.y > 0) {
                    columnHeightFraction = (columnHeightFraction - 0.05f).coerceAtLeast(0.76f)
                }
                return Offset.Zero
            }
        }
    }


    Box(modifier = Modifier.fillMaxSize().nestedScroll(nestedScrollConnection)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .background(color = Color.Green)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(animatedHeight)
                .background(
                    color = Color.Gray,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(top = 32.dp, start = 32.dp)
                .align(Alignment.BottomCenter)
        ) {
            item {Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "스파르타 멘토님")
                Text(text = "소요시간 16분", modifier = Modifier.padding(end = 32.dp))
            }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "작심삼일을 극복하는\n초집중력과 루틴 만들기")
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "레벨")
                    Text(text = " 별별별")
                    Text(text = " 적당히 강하게")
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "결심은 약하다. 행동만이 너를 강하게 만든다!")
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "멘트링 스타일")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "망설임을 없애고 즉시 실행하는 강철 멘탈 코칭!")
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "배울점")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "- 작심삼일을 극복하는 스파르타식 마인드셋")
                Text(text = "- 작심삼일을 극복하는 스파르타식 마인드셋")
                Text(text = "- 작심삼일을 극복하는 스파르타식 마인드셋")
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "강의 리스트")
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    items(3) {
                        QuestCard()
                    }
                }
                Spacer(modifier = Modifier.height(200.dp))

            }

        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 80.dp, start = 32.dp, end = 32.dp)
            .align(Alignment.BottomCenter)
            .background(color = Color.Green)
            .clickable {
                clickNext()
            }) {
            Text(
                text = "시작하기", modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 20.dp)
            )
        }
    }
}

@Composable
private fun QuestCard() {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(166.dp)
            .clip(shape = RoundedCornerShape(10))
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .background(color = Color.Green)
                .align(Alignment.TopEnd)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(text = "1강")
            Text(text = "결심을 넘어\n행동으로")
        }
    }
}

@Preview
@Composable
fun QuestCardPreview() {
    QuestCard()
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun MentorDetailScreenPreview() {
    MentorDetailScreen()
}