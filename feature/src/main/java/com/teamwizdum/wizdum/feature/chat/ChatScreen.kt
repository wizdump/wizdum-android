package com.teamwizdum.wizdum.feature.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.extension.noRippleClickable
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black300
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.chat.component.ChatBubble
import com.teamwizdum.wizdum.feature.chat.component.ChatExitDialog
import com.teamwizdum.wizdum.feature.chat.component.ChatFinishSelectionBox
import com.teamwizdum.wizdum.feature.chat.component.ChatHeader
import com.teamwizdum.wizdum.feature.chat.component.ChatStartSelectionBox
import com.teamwizdum.wizdum.feature.chat.component.ChatTextField
import com.teamwizdum.wizdum.feature.chat.component.ChatWithProfileBubble
import com.teamwizdum.wizdum.feature.chat.component.TypingIndicatorBubble
import com.teamwizdum.wizdum.feature.chat.info.MessageType
import com.teamwizdum.wizdum.feature.common.base.UiState
import com.teamwizdum.wizdum.feature.common.enums.LectureStatus
import com.teamwizdum.wizdum.feature.lecture.navigation.argument.LectureArgument
import com.teamwizdum.wizdum.feature.lecture.navigation.argument.LectureClearArgument

@Composable
fun ChatRoute(
    viewModel: ChatViewModel = hiltViewModel(),
    lectureInfo: LectureArgument,
    onNavigateBack: () -> Unit,
    onNavigateToClear: (LectureClearArgument) -> Unit,
    onNavigateToAllClear: (Int, String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(lectureInfo.lectureId)
    }

    val uiState = viewModel.chatUiState.collectAsState().value
    val messageList = viewModel.messages.collectAsState().value

    var dialogState by remember { mutableStateOf(false) }

    when(uiState) {
        is UiState.Loading -> {}
        is UiState.Success -> {
            ChatScreen(
                viewModel = viewModel,
                lectureInfo = lectureInfo,
                messages = messageList,
                showDialog = { dialogState = true },
                onNavigateBack = onNavigateBack,
                onNavigateToClear = {
                    viewModel.finishLecture(lectureInfo.lectureId) { encouragement ->
                        onNavigateToClear(
                            LectureClearArgument(
                                classId = lectureInfo.classId,
                                lectureId = lectureInfo.lectureId,
                                orderSeq = lectureInfo.orderSeq,
                                mentorName = lectureInfo.mentorName,
                                lectureName = lectureInfo.lectureTitle,
                                encouragement = encouragement
                            )
                        )
                    }
                },
                onNavigateToAllClear = {
                    viewModel.finishLecture(lectureInfo.lectureId) {
                        onNavigateToAllClear(
                            lectureInfo.classId,
                            lectureInfo.mentorName
                        )
                    }
                }
            )
        }
        is UiState.Failed -> {}
    }

    ChatExitDialog(
        dialogState = dialogState,
        onExit = {
            dialogState = false
            onNavigateBack()
        },
        onDismissRequest = { dialogState = false }
    )
}

@Composable
private fun ChatScreen(
    viewModel: ChatViewModel,
    lectureInfo: LectureArgument,
    messages: List<ChatMessage>,
    showDialog: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToClear: () -> Unit,
    onNavigateToAllClear: () -> Unit,
) {
    val listState = rememberLazyListState()

    val isReceivingMessage = viewModel.isReceiving.collectAsState().value
    val isLectureFinished = viewModel.isFinish.collectAsState().value

    var isStartBoxVisible by remember { mutableStateOf(messages.isEmpty()) }
    var isFinishBoxVisible by remember {
        mutableStateOf(viewModel.shouldShowFinishState(lectureInfo.lectureStatus))
    }
    var isOnGoing by remember { mutableStateOf(viewModel.isChatOngoing(lectureInfo.lectureStatus)) }
    var isInputEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(isLectureFinished) {
        if (isLectureFinished) {
            isFinishBoxVisible = true
        }
    }

    LaunchedEffect(
        isReceivingMessage,
        isStartBoxVisible,
        isFinishBoxVisible,
        isInputEnabled
    ) {
        isInputEnabled = when {
            isReceivingMessage -> false
            (messages.isEmpty() && isStartBoxVisible) -> false
            isFinishBoxVisible -> false
            lectureInfo.lectureStatus == LectureStatus.DONE.name -> false
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
            onNavigateBack = onNavigateBack,
            actions = {
                if (isOnGoing)
                    Row(
                        modifier = Modifier.noRippleClickable {
                            if (lectureInfo.isLastLecture) {
                                onNavigateToAllClear()
                            } else {
                                onNavigateToClear()
                            }
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
            modifier = Modifier.weight(1f),
            listState = listState,
            isReceiving = isReceivingMessage,
            isStartBoxVisible = isStartBoxVisible,
            isFinishBoxVisible = isFinishBoxVisible,
            lectureInfo = lectureInfo,
            messageList = messages,
            onStartChat = { isStartBoxVisible = false },
            onGoingChat = {
                isOnGoing = true
                isFinishBoxVisible = false
            },
            sendMessage = { isHide, text ->
                viewModel.sendMessage(
                    name = lectureInfo.userName,
                    lectureId = lectureInfo.lectureId,
                    message = text,
                    isHide = isHide
                )
            },
            showDialog = showDialog,
            onNavigateToClear = {
                onNavigateToClear()
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
    isStartBoxVisible: Boolean,
    isFinishBoxVisible: Boolean,
    lectureInfo: LectureArgument,
    messageList: List<ChatMessage>,
    onStartChat: () -> Unit,
    onGoingChat: () -> Unit,
    sendMessage: (Boolean, String) -> Unit,
    showDialog: () -> Unit,
    onNavigateToClear: () -> Unit,
    onNavigateToAllClear: () -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .background(color = Black100),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        state = listState
    ) {
        item {
            ChatHeader(
                messageList = messageList,
                mentorName = lectureInfo.mentorName
            )

            if (isStartBoxVisible) {
                ChatStartSelectionBox(
                    mentorName = lectureInfo.mentorName,
                    orderSeq = lectureInfo.orderSeq,
                    onStart = {
                        onStartChat()
                        sendMessage(true, "인사해줘")
                    },
                    onNavigateBack = {
                        showDialog()
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
                        name = lectureInfo.mentorName,
                        imgUrl = lectureInfo.mentorImgUrl
                    )
                }
            }
        }

        if (isFinishBoxVisible) {
            item {
                ChatFinishSelectionBox(
                    orderSeq = lectureInfo.orderSeq,
                    isLastLecture = lectureInfo.isLastLecture,
                    onGoing = {
                        onGoingChat()
                    },
                    onNavigateToClear = onNavigateToClear,
                    onNavigateToAllClear = onNavigateToAllClear
                )
            }
        }

        if (isReceiving) {
            item {
                TypingIndicatorBubble(
                    name = lectureInfo.mentorName,
                    imgUrl = lectureInfo.mentorImgUrl
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}