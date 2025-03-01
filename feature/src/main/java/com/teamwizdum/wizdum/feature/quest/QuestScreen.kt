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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.teamwizdum.wizdum.data.model.response.Lecture
import com.teamwizdum.wizdum.data.model.response.LectureDetail
import com.teamwizdum.wizdum.data.model.response.QuestResponse
import com.teamwizdum.wizdum.designsystem.component.TextBadge
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black700
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.onboarding.component.LevelInfo

@Composable
fun QuestScreen(navController: NavHostController, viewModel: QuestViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getQuest(1)
    }

    val questInfo = viewModel.quests.collectAsState().value

    QuestContent(questInfo)
}

@Composable
private fun QuestContent(questInfo: QuestResponse) {
    var columnHeightFraction by remember { mutableStateOf(0.6f) }
    val animatedHeight by animateFloatAsState(targetValue = columnHeightFraction, label = "")

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < 0) {
                    columnHeightFraction = (columnHeightFraction + 0.05f).coerceAtMost(1f)
                } else if (available.y > 0) {
                    columnHeightFraction =
                        (columnHeightFraction - 0.05f).coerceAtLeast(0.6f) // TODO: 0.6f 비율 아닌 최소 길이 확보
                }
                return Offset.Zero
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection) // TODO: 드래그 뻣뻣한 부분 수정
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(top = 92.dp, start = 32.dp, end = 32.dp)
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .background(color = Color.Green, shape = CircleShape)
                        .width(42.dp)
                        .height(42.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = questInfo.mentoName, style = WizdumTheme.typography.body1_semib)
                    Text(text = "유니님의 멘토", style = WizdumTheme.typography.body2)
                    //Wiz 획득 뱃지
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "작심삼일을 극복하는\n초집중력과 루틴 만들기", style = WizdumTheme.typography.h2)
            Spacer(modifier = Modifier.height(8.dp))
            LevelInfo(level = questInfo.level)
            Spacer(modifier = Modifier.height(32.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "목표", style = WizdumTheme.typography.body1)
                Spacer(modifier = Modifier.width(8.dp))
                QuestProgressBar(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "1 / 3")

            }
            Spacer(modifier = Modifier.height(32.dp))
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(animatedHeight)
                .background(
                    color = Black100,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(top = 32.dp, start = 24.dp, end = 24.dp)
                .align(Alignment.BottomCenter)
        ) {

            itemsIndexed(questInfo.lectures, key = { index, item -> item.lectureId }) { index, item ->
                QuestItem(item)
            }
        }
        BackAppBar()
    }
}

@Composable
private fun QuestProgressBar(modifier: Modifier = Modifier, progress: Float = 0.3f) {
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
private fun QuestItem(lecture: LectureDetail) {
    var rowHeight by remember { mutableStateOf(71) }

    Row {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            StatusCircle(isFocused = false, status = 1)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                Modifier
                    .height(with(LocalDensity.current) { rowHeight.toDp() })
                    .width(1.dp)
                    .background(color = Color.Black) //포커스이거나 or 진행중인 경우 초록색 아니면 검은색
            )

        }
        Spacer(modifier = Modifier.width(16.dp))
        ExpandableQuestCard(lecture) { newHeight ->
            rowHeight = newHeight
        }
    }
}

@Composable
private fun StatusCircle(isFocused: Boolean, status: Int) {
    var imageResource = painterResource(id = R.drawable.ic_quest_start)

    // 포커스된 강의가 아닐 때
    if (!isFocused) {
        if (status == 1) {
            // 완료일 때
            imageResource = painterResource(id = R.drawable.ic_not_completed)

        } else {
            // 미완료일 때
            imageResource = painterResource(id = R.drawable.ic_quest_finished)
        }
    }

    Icon(painter = imageResource, contentDescription = "퀘스트 상태")
}



@Composable
fun ExpandableQuestCard(lecture: LectureDetail, isExpanded: Boolean = true, onHeightChanged: (Int) -> Unit = {}) {
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
                    text = "소요시간 ${lecture.courseTime}분",
                    style = WizdumTheme.typography.body3,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                    TextBadge("1강")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = lecture.title, style = WizdumTheme.typography.h3_semib)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "강의를 통해",
                    style = WizdumTheme.typography.body3,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = lecture.preview,
                    style = WizdumTheme.typography.body3_semib,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            QuestStartButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                isFinished = true,
                onNavigateNext = {}
            )
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
private fun QuestStartButton(
    modifier: Modifier,
    isFinished: Boolean,
    onNavigateNext: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = if (isFinished) Color.White else WizdumTheme.colorScheme.primary)
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .clickable {
                onNavigateNext()
            },
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isFinished) "다시하기" else "시작하기",
                style = WizdumTheme.typography.body1,
                color = if (isFinished) Black700 else Color.White,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_btn_arrow_up),
                contentDescription = "접기",
                modifier = Modifier.clickable {

                }
            )
        }
    }
}

//@Composable
//private fun QuestRetryButton() {
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(48.dp)
//            .background(color = if (isFinished) Color.White else WizdumTheme.colorScheme.primary)
//            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
//            .clickable {
//                onNavigateNext()
//            },
//        contentAlignment = Alignment.Center
//    ) {
//        Row(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = if (isFinished) "다시하기" else "시작하기",
//                style = WizdumTheme.typography.body1,
//                color = if (isFinished) Black700 else Color.White,
//            )
//            Icon(
//                painter = painterResource(id = R.drawable.ic_btn_arrow_up),
//                contentDescription = "접기",
//                modifier = Modifier.clickable {
//
//                }
//            )
//        }
//    }
//
//}

@Preview
@Composable
fun CustomProgressBarPreview() {
    WizdumTheme {
        QuestProgressBar()
    }
}

//@Preview(showBackground = true)
//@Composable
//fun QuestItemPreview() {
//    WizdumTheme {
//        QuestItem(index = 0)
//    }
//}
//
//@Preview
//@Composable
//fun ExpandableQuestCardPreview() {
//    WizdumTheme {
//        Column {
//            ExpandableQuestCard(isExpanded = false)
//            ExpandableQuestCard(isExpanded = true)
//        }
//    }
//}

//@Preview(showBackground = true, widthDp = 360, heightDp = 800)
//@Composable
//fun QuestScreenPreview() {
//    WizdumTheme {
//        QuestContent()
//    }
//}
