package com.teamwizdum.wizdum.feature.quest

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.component.TextBadge
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun QuestScreen() {
    var columnHeightFraction by remember { mutableStateOf(0.6f) }
    val animatedHeight by animateFloatAsState(targetValue = columnHeightFraction, label = "")

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < 0) {
                    columnHeightFraction = (columnHeightFraction + 0.05f).coerceAtMost(1f)
                } else if (available.y > 0) {
                    columnHeightFraction = (columnHeightFraction - 0.05f).coerceAtLeast(0.6f) // TODO: 0.6f 비율 아닌 최소 길이 확보
                }
                return Offset.Zero
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .nestedScroll(nestedScrollConnection) // TODO: 드래그 뻣뻣한 부분 수정
    ) {
        Column(Modifier.padding(start = 32.dp, end = 32.dp)) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .background(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(48.dp))
            Row {
                Box(
                    modifier = Modifier
                        .background(color = Color.Green, shape = CircleShape)
                        .width(42.dp)
                        .height(42.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = "스파르타", style = WizdumTheme.typography.body1_semib)
                    Text(text = "유니님의 멘토", style = WizdumTheme.typography.body2)
                    //Wiz 획득 뱃지
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "작심삼일을 극복하는\n초집중력과 루틴 만들기", style = WizdumTheme.typography.h2)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "레벨", style = WizdumTheme.typography.body2)
                Text(text = "⭐⭐⭐", style = WizdumTheme.typography.body2)
                Text(text = "적당히 강하게", style = WizdumTheme.typography.body2)
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "목표", style = WizdumTheme.typography.body1)
                Spacer(modifier = Modifier.width(8.dp))
                CustomProgressBar(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "1 / 3")

            }
            Spacer(modifier = Modifier.height(32.dp))
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(animatedHeight)
                .background(color = Color.Gray)
                .padding(
                    top = 32.dp, start = 24.dp, end = 24.dp
                )
                .align(Alignment.BottomCenter)
        ) {
            items(3, key = { it }) {// TODO: 드래그 뻣뻣한 부분 수정
                it
                QuestItem(it)
            }
        }
    }
}

@Composable
fun QuestItem(index: Int) {
    var rowHeight by remember { mutableStateOf(71) }

    Row {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .background(color = Color.Green, shape = CircleShape)
                    .width(20.dp)
                    .height(20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (index != 2)
                Box(
                    Modifier
                        .height(with(LocalDensity.current) { rowHeight.toDp() })
                        .width(3.dp)
                        .background(color = Color.Black)
                )
        }
        Spacer(modifier = Modifier.width(16.dp))
        ExpandableQuestCard() { newHeight ->
            rowHeight = newHeight
        }
    }
}

@Composable
fun CustomProgressBar(modifier: Modifier = Modifier, progress: Float = 0.3f) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(color = Color.Gray, shape = RoundedCornerShape(10.dp))
    ) {
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
        )
        val parentWidth = constraints.maxWidth

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
            )
        }

        // 진입 할 때 프로그래스바가 에니메이션으로 채워져야 함
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width((animatedProgress * parentWidth).dp)
                .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
        )
    }
}

@Composable
fun ExpandableQuestCard(isExpanded: Boolean = true, onHeightChanged: (Int) -> Unit = {}) {
    // 수강 완료된 강의는 축소, 아래 방향을 누르면 다시 확장시킬 수 있음
    var isExpanded by remember { mutableStateOf(isExpanded) }
    val cardHeight by animateDpAsState(
        targetValue = if (isExpanded) 192.dp else 71.dp,
        label = "",
        finishedListener = { } // 애니메이션이 끝났을 때 높이
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .height(cardHeight)
            .background(color = Color.White)
            .onSizeChanged { onHeightChanged(it.height) }
    ) {
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextBadge(title = "수강 전", modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        isExpanded = false // 임시조치
                    })
                Text(
                    text = "소요시간 6분",
                    style = WizdumTheme.typography.body3,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                    TextBadge("1강")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "결심을 넘어서 행동으로", style = WizdumTheme.typography.h3_semib)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "강의를 통해",
                    style = WizdumTheme.typography.body3,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "즉각적인 실행력과 지속적인 루틴을 구축할거에요.",
                    style = WizdumTheme.typography.body3_semib,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            QuestStartButton(modifier = Modifier.align(Alignment.BottomCenter))
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row {
                        Text(text = "1강", style = WizdumTheme.typography.body3)
                        Text(text = " 수강완료", style = WizdumTheme.typography.body3_semib)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "결심을 넘어 행동으로", style = WizdumTheme.typography.body2_semib)
                }

                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .background(color = Color.Black)
                        .clickable {
                            isExpanded = true
                        }
                )
            }
        }
    }
}

@Composable
fun QuestStartButton(modifier: Modifier, index: Int = 0, status: String = "") {
    // 3types - 시작하기 활성화, 시작하기 비활성화, 다시하기
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.Green)
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .clickable {
                //clickNext()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "시작하기", style = WizdumTheme.typography.body1)
    }
}

enum class QuestStatus {
    STUDING,
    COMPLETED
}

@Preview
@Composable
fun CustomProgressBarPreview() {
    WizdumTheme {
        CustomProgressBar()
    }
}

@Preview(showBackground = true)
@Composable
fun QuestItemPreview() {
    WizdumTheme {
        QuestItem(index = 0)
    }
}

@Preview
@Composable
fun ExpandableQuestCardPreview() {
    WizdumTheme {
        Column {
            ExpandableQuestCard(isExpanded = false)
            ExpandableQuestCard(isExpanded = true)
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun QuestScreenPreview() {
    WizdumTheme {
        QuestScreen()
    }
}
