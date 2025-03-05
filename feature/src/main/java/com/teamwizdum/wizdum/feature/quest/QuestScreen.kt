package com.teamwizdum.wizdum.feature.quest

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwizdum.wizdum.data.model.response.LectureDetail
import com.teamwizdum.wizdum.data.model.response.QuestResponse
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.component.badge.TextBadge
import com.teamwizdum.wizdum.designsystem.component.badge.TextWithIconBadge
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black300
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.Green100
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.Success
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.onboarding.component.LevelInfo
import com.teamwizdum.wizdum.feature.quest.component.QuestProgressBar
import com.teamwizdum.wizdum.feature.quest.component.QuestStatusBadge
import com.teamwizdum.wizdum.feature.quest.component.StatusCircle
import com.teamwizdum.wizdum.feature.quest.info.ChatRoomInfo
import com.teamwizdum.wizdum.feature.quest.info.QuestStatus
import timber.log.Timber

@Composable
fun QuestScreen(
    viewModel: QuestViewModel,
    onNavigateToChat: () -> Unit,
    onNavigateToQuestALlClear: (Int, String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getQuest(1)
    }

    val questInfo = viewModel.quests.collectAsState().value
    Timber.d("### viewModel: ${viewModel.hashCode()}")

    QuestContent(
        questInfo = questInfo,
        onNavigateToChat = { lectureId, orderSeq, lectureStatus ->
            viewModel.updateChatRoomInfo(
                ChatRoomInfo(
                    mentorName = questInfo.mentoName,
                    mentorImgUrl = questInfo.filePath ?: "",
                    userName = questInfo.userName,
                    lectureTitle = questInfo.mentoLectureTitle,
                    lectureId = lectureId,
                    orderSeq = orderSeq,
                    lectureStatus = lectureStatus,
                    isLastLecture = questInfo.isLastLecture(orderSeq),
                )
            )
            onNavigateToChat()
        },
        onNavigateToQuestALlClear
    )
}

@Composable
fun QuestContent(
    questInfo: QuestResponse,
    onNavigateToChat: (Int, Int, String) -> Unit,
    onNavigateToReward: (Int, String) -> Unit,
) {
    val minHeightPx = with(LocalDensity.current) { 310.dp.toPx() } // 상단 정보를 보여주기 위한 최소 높이
    val screenHeightPx =
        with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.toPx() }

    val initialHeightFraction = (screenHeightPx - minHeightPx) / screenHeightPx

    var columnHeightFraction by remember { mutableStateOf(initialHeightFraction) }
    val animatedHeight by animateFloatAsState(targetValue = columnHeightFraction, label = "")

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < 0) {
                    columnHeightFraction = (columnHeightFraction + 0.05f).coerceAtMost(1f)
                } else if (available.y > 0) {
                    columnHeightFraction =
                        (columnHeightFraction - 0.05f).coerceAtLeast(initialHeightFraction)
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
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(questInfo.filePath)
                .crossfade(true)
                .build(),
            contentDescription = "강의 배경 이미지",
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Black600)
        )
        Column(
            modifier = Modifier.padding(top = 92.dp, start = 32.dp, end = 32.dp)
        ) {
            Row {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(questInfo.filePath)
                        .crossfade(true)
                        .build(),
                    contentDescription = "멘토 프로필 이미지",
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(color = Black100)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = questInfo.mentoName,
                        style = WizdumTheme.typography.body1_semib,
                        color = Color.White
                    )
                    Text(
                        text = "${questInfo.userName}님의 멘토",
                        style = WizdumTheme.typography.body2,
                        color = Color.White
                    )
                }
                if (questInfo.canGetWiz) {
                    TextWithIconBadge(
                        title = "Wiz 획득",
                        resId = R.drawable.ic_checked,
                        backgroundColor = Green200,
                        textColor = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = questInfo.mentoLectureTitle,
                style = WizdumTheme.typography.h2,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            LevelInfo(level = questInfo.level)
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "목표", style = WizdumTheme.typography.body1, color = Color.White)
                QuestProgressBar(
                    modifier = Modifier.weight(1f),
                    progress = questInfo.getProgress()
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Green200)) {
                            append("${questInfo.getFinishedLectureCount()}")
                        }
                        append(" / ${questInfo.lectures.size}")
                    },
                    style = WizdumTheme.typography.body1,
                    color = Black200
                )
            }
        }

        BackAppBar(isDark = true)

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
            items(questInfo.lectures, key = { it.lectureId }) { lecture ->
                QuestItem(
                    lecture = lecture,
                    onNavigateToChat = {
                        onNavigateToChat(
                            lecture.lectureId,
                            lecture.orderSeq,
                            lecture.lectureStatus
                        )
                    }
                )
            }
        }

        if (questInfo.isFinished && !questInfo.canGetWiz) {
            WizdumFilledButton(
                title = "리워드 받기",
                modifier = Modifier
                    .padding(bottom = 80.dp, start = 32.dp, end = 32.dp)
                    .align(Alignment.BottomCenter)
            ) {
                onNavigateToReward(questInfo.mentoId, questInfo.mentoName)
            }
        }
    }
}

@Composable
fun QuestItem(lecture: LectureDetail, onNavigateToChat: () -> Unit) {
    var rowHeight by remember { mutableStateOf(71) }

    Row {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            StatusCircle(isInProgress = lecture.isInProgress, status = lecture.lectureStatus)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                Modifier
                    .height(with(LocalDensity.current) { rowHeight.toDp() - 6.dp })
                    .width(1.dp)
                    .background(color = if (lecture.isInProgress || lecture.lectureStatus != QuestStatus.WAIT.name) Green200 else Black300)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        ExpandableQuestCard(
            lectureInfo = lecture,
            onHeightChanged = { newHeight -> rowHeight = newHeight },
            onNavigateToChat = { onNavigateToChat() }
        )
    }
}

@Composable
fun ExpandableQuestCard(
    lectureInfo: LectureDetail,
    onHeightChanged: (Int) -> Unit = {},
    onNavigateToChat: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(lectureInfo.isInProgress) }
    val cardHeight by animateDpAsState(
        targetValue = if (isExpanded) 186.dp else if (!isExpanded && lectureInfo.lectureStatus == QuestStatus.WAIT.name) 95.dp else 71.dp,
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
                    .padding(top = 8.dp, start = 16.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuestStatusBadge(
                    status = lectureInfo.lectureStatus,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextBadge(
                        "${lectureInfo.orderSeq}강",
                        backgroundColor = Green100,
                        borderColor = Green100,
                        textColor = WizdumTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = lectureInfo.title, style = WizdumTheme.typography.h3_semib)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "강의를 통해",
                    style = WizdumTheme.typography.body3,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "\uD83D\uDC49 ${lectureInfo.preview}",
                    style = WizdumTheme.typography.body3_semib,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (lectureInfo.lectureStatus == QuestStatus.DONE.name) {
                QuestRetryButton(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onNavigateToChat = { onNavigateToChat() },
                    onClick = { isExpanded = false }
                )
            } else {
                QuestStartButton(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onNavigateToChat = { onNavigateToChat() }
                )
            }
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
                        Text(
                            text = "${lectureInfo.orderSeq}강",
                            style = WizdumTheme.typography.body3
                        )

                        if (lectureInfo.lectureStatus == QuestStatus.DONE.name) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = " 수강완료",
                                    style = WizdumTheme.typography.body3_semib,
                                    color = Success
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.ic_badge_done),
                                    contentDescription = "수강완료"
                                )
                            }
                        } else {
                            Text(text = " 수강 전", style = WizdumTheme.typography.body3_semib)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = lectureInfo.title, style = WizdumTheme.typography.body2_semib)

                    if (lectureInfo.lectureStatus == QuestStatus.WAIT.name) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = lectureInfo.preview, style = WizdumTheme.typography.body3)
                    }
                }

                if (lectureInfo.lectureStatus != QuestStatus.WAIT.name) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_btn_arrow_down),
                        contentDescription = "펼치기",
                        modifier = Modifier.clickable { isExpanded = true }
                    )
                }
            }
        }
    }
}

@Composable
fun QuestStartButton(
    modifier: Modifier,
    onNavigateToChat: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = WizdumTheme.colorScheme.primary)
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .clickable {
                onNavigateToChat()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "시작하기",
            style = WizdumTheme.typography.body1,
            color = Color.White,
        )
    }
}

@Composable
fun QuestRetryButton(
    modifier: Modifier,
    onNavigateToChat: () -> Unit,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(color = Color.White)
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column {
            HorizontalDivider(thickness = 1.dp, color = Black300)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "다시보기",
                    style = WizdumTheme.typography.body1,
                    modifier = modifier.clickable {
                        onNavigateToChat()
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_btn_arrow_up),
                    contentDescription = "접기",
                    modifier = Modifier.clickable {
                        onClick()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ExpandableQuestCardPreview() {
    WizdumTheme {
        Column {
            ExpandableQuestCard(
                lectureInfo = LectureDetail(
                    lectureId = 1,
                    orderSeq = 1,
                    title = "결심을 넘어 행동으로!",
                    preview = "즉각적인 실행력과 지속적인 루틴을 구축할거에요.",
                    lectureStatus = "IN_PROGRESS",
                    isInProgress = true,
                    isFinished = false
                ),
            ) {}
            ExpandableQuestCard(
                lectureInfo = LectureDetail(
                    lectureId = 1,
                    orderSeq = 1,
                    title = "결심을 넘어 행동으로!",
                    preview = "즉각적인 실행력과 지속적인 루틴을 구축할거에요.",
                    lectureStatus = "DONE",
                    isInProgress = false,
                    isFinished = true
                )
            ) {}
            ExpandableQuestCard(
                lectureInfo = LectureDetail(
                    lectureId = 1,
                    orderSeq = 1,
                    title = "결심을 넘어 행동으로!",
                    preview = "즉각적인 실행력과 지속적인 루틴을 구축할거에요.",
                    lectureStatus = "WAIT",
                    isInProgress = false,
                    isFinished = false
                )
            ) {}
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun QuestScreenPreview() {
    WizdumTheme {
        QuestContent(
            questInfo = QuestResponse(
                mentoName = "스파르타",
                userName = "유니",
                mentoLectureTitle = "작심삼일을 극복하는\n초집중력과 루틴 만들기",
                level = "HIGH",
                lectures = listOf(
                    LectureDetail(
                        lectureId = 1,
                        orderSeq = 1,
                        title = "결심을 넘어 행동으로!",
                        preview = "즉각적인 실행력과 지속적인 루틴을 구축할거에요.",
                        lectureStatus = "IN_PROGRESS",
                        isInProgress = true,
                        isFinished = false
                    ),
                    LectureDetail(
                        lectureId = 2,
                        orderSeq = 2,
                        title = "결심을 넘어 행동으로!",
                        preview = "즉각적인 실행력과 지속적인 루틴을 구축할거에요.",
                        lectureStatus = "WAIT",
                        isInProgress = false,
                        isFinished = false
                    ),
                    LectureDetail(
                        lectureId = 3,
                        orderSeq = 3,
                        title = "결심을 넘어 행동으로!",
                        preview = "즉각적인 실행력과 지속적인 루틴을 구축할거에요.",
                        lectureStatus = "WAIT",
                        isInProgress = false,
                        isFinished = false
                    )
                )
            ),
            onNavigateToChat = { _, _, _ -> },
            onNavigateToReward = { _, _ -> }
        )
    }
}
