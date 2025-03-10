package com.teamwizdum.wizdum.feature.chat

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black300
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.chat.component.ChatBubble
import com.teamwizdum.wizdum.feature.chat.component.ChatFinishSelectionBox
import com.teamwizdum.wizdum.feature.chat.component.ChatHeader
import com.teamwizdum.wizdum.feature.chat.component.ChatStartSelectionBox
import com.teamwizdum.wizdum.feature.chat.component.ChatTextField
import com.teamwizdum.wizdum.feature.chat.component.ChatWithProfileBubble
import com.teamwizdum.wizdum.feature.chat.info.MessageType
import com.teamwizdum.wizdum.feature.quest.info.QuestStatus
import com.teamwizdum.wizdum.feature.quest.navigation.argument.LectureArgument
import com.teamwizdum.wizdum.feature.quest.navigation.argument.LectureClearArgument

@Composable
fun ChatRoute(
    viewModel: ChatViewModel = hiltViewModel(),
    lectureInfo: LectureArgument,
    onNavigateToClear: (LectureClearArgument) -> Unit,
    onNavigateToAllClear: (Int, String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(lectureInfo.lectureId)
    }

    val messages = viewModel.messages.collectAsState().value

    ChatScreen(
        viewModel = viewModel,
        lectureInfo = lectureInfo,
        messages = messages,
        onNavigateToClear = { onNavigateToClear(it) },
        onNavigateToAllClear = {
            onNavigateToAllClear(
                lectureInfo.lectureId,
                lectureInfo.mentorName
            )
        }
    )
}

@Composable
private fun ChatScreen(
    viewModel: ChatViewModel,
    lectureInfo: LectureArgument,
    messages: List<ChatMessage>,
    onNavigateToClear: (LectureClearArgument) -> Unit,
    onNavigateToAllClear: () -> Unit,
) {
    val listState = rememberLazyListState()

    val isReceivingMessage = viewModel.isReceiving.collectAsState().value
    var isSelectionBoxVisible by remember { mutableStateOf(messages.isEmpty()) }
    var isInputEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(isReceivingMessage, isSelectionBoxVisible, isInputEnabled) {
        isInputEnabled = when {
            isReceivingMessage -> false
            isSelectionBoxVisible -> false
            lectureInfo.lectureStatus == QuestStatus.DONE.name -> false
            else -> true
        }
    }

    // 마지막 메세지로 자동 스크롤
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        BackAppBar(
            title = "${lectureInfo.orderSeq}강 · ${lectureInfo.lectureTitle}",
            actions = {
                if (viewModel.checkOngoing())
                    Row(
                        modifier = Modifier.clickable {
                            // 종료 처리
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_btn_exit),
                            contentDescription = "대화 종료 및 나가기"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "대화종료",
                            style = WizdumTheme.typography.body2,
                            color = WizdumTheme.colorScheme.error
                        )
                    }
            }
        )
        HorizontalDivider(thickness = 1.dp, color = Black300)

        ChatMessages(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            listState = listState,
            isReceiving = isReceivingMessage,
            isSelectionBoxVisible = isSelectionBoxVisible,
            mentorName = lectureInfo.mentorName,
            mentorImgUrl = lectureInfo.mentorImgUrl,
            isLastLecture = lectureInfo.isLastLecture,
            messageList = messages,
            onStartChat = { isSelectionBoxVisible = false },
            sendMessage = { isHide, text ->
                viewModel.sendMessage(
                    name = lectureInfo.userName,
                    lectureId = lectureInfo.lectureId,
                    message = text,
                    isHide = isHide
                )
            },
            onNavigateToClear = {
                viewModel.finishQuest(1) { encouragement ->
                    onNavigateToClear(
                        LectureClearArgument(
                            lectureId = lectureInfo.lectureId,
                            orderSeq = lectureInfo.orderSeq,
                            lectureName = lectureInfo.lectureTitle,
                            mentorName = lectureInfo.mentorName,
                            encouragement = encouragement
                        )
                    )
                }
            },
            onNavigateToAllClear = {
                onNavigateToAllClear()
            }
        )

        ChatTextField(
            isInputEnabled = isInputEnabled,
            sendMessage = { text ->
                viewModel.setReceiving(true)
                viewModel.sendMessage(
                    name = lectureInfo.userName,
                    lectureId = lectureInfo.lectureId,
                    message = text
                )
            }
        )
    }
}

@Composable
fun ChatMessages(
    modifier: Modifier,
    listState: LazyListState,
    isReceiving: Boolean = false,
    isSelectionBoxVisible: Boolean,
    mentorName: String,
    mentorImgUrl: String,
    isLastLecture: Boolean,
    messageList: List<ChatMessage>,
    onStartChat: () -> Unit,
    sendMessage: (Boolean, String) -> Unit,
    onNavigateToClear: () -> Unit,
    onNavigateToAllClear: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .background(color = Black100),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        state = listState
    ) {
        item {
            ChatHeader(messageList = messageList, mentorName = mentorName)

            if (isSelectionBoxVisible) {
                ChatStartSelectionBox(
                    onStart = {
                        onStartChat()
                        sendMessage(true, "대화해줘")
                    },
                    onNavigateBack = {
                        /* 채팅방 나가기 */
                    }
                )
            }
        }

        items(messageList) { chat ->
            when (chat.type) {
                MessageType.USER_REQUEST.name -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        ChatBubble(message = chat.message.content)
                    }
                }

                MessageType.MENTOR_RESPONSE.name -> {
                    ChatWithProfileBubble(
                        message = chat.message.content,
                        imgUrl = mentorImgUrl
                    )
                }
            }

            if (chat.message.isFinish && !chat.message.isOngoing) {
                ChatFinishSelectionBox(
                    isLastLecture = isLastLecture,
                    onGoing = {
                        // 사라지고
                        // 상단 나가기 버튼 보여주기
                    },
                    onNavigateToClear = onNavigateToClear,
                    onNavigateToAllClear = onNavigateToAllClear
                )
            }
        }

        if (isReceiving) {
            item {
                ChatWithProfileBubble(
                    message = "...",
                    imgUrl = mentorImgUrl,
                )
            }
        }
    }
}

@Composable
fun LoadingDots() {
    val dotCount = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            dotCount.animateTo(
                targetValue = 3f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
            dotCount.snapTo(1f)
        }
    }

    Text(
        text = "●".repeat(dotCount.value.toInt()),
        fontSize = 20.sp,
        color = Color.Gray
    )
}