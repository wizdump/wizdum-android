package com.teamwizdum.wizdum.feature.chat

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black300
import com.teamwizdum.wizdum.designsystem.theme.Black500
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.designsystem.theme.robotoFontFamily
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.chat.component.ChatBubble
import com.teamwizdum.wizdum.feature.chat.component.ChatFinishSelectionBox
import com.teamwizdum.wizdum.feature.chat.component.ChatHeader
import com.teamwizdum.wizdum.feature.chat.component.ChatStartSelectionBox
import com.teamwizdum.wizdum.feature.chat.component.ChatWithProfileBubble
import com.teamwizdum.wizdum.feature.chat.info.MessageType
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

    val message = viewModel.message.collectAsState().value

    ChatScreen(
        lectureInfo = lectureInfo,
        message = message,
        viewModel = viewModel,
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
    lectureInfo: LectureArgument,
    message: List<ChatMessage>,
    viewModel: ChatViewModel,
    onNavigateToClear: (LectureClearArgument) -> Unit,
    onNavigateToAllClear: () -> Unit,
) {
    val listState = rememberLazyListState()

    val isReceivingMessage = viewModel.isReceiving.collectAsState().value
    var isInputEnabled by remember { mutableStateOf(true) }

    if (lectureInfo.lectureStatus == "DONE") {
        isInputEnabled = false
    }

    LaunchedEffect(isReceivingMessage) {
        isInputEnabled = !isReceivingMessage
    }

    LaunchedEffect(message.size) {
        if (message.isNotEmpty()) {
            listState.animateScrollToItem(message.lastIndex)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackAppBar(title = "${lectureInfo.orderSeq}강 - ${lectureInfo.lectureTitle}")
        HorizontalDivider(thickness = 1.dp, color = Black300)
        ChatMessages(
            isReceiving = isReceivingMessage,
            sendMessage = { isHide, text ->
                viewModel.sendMessage(
                    name = lectureInfo.userName,
                    lectureId = lectureInfo.lectureId,
                    message = text,
                    isHide = isHide
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            mentorName = lectureInfo.mentorName,
            mentorImgUrl = lectureInfo.mentorImgUrl,
            messageList = message,
            listState = listState,
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
            }
        )
        ChatTextField(viewModel = viewModel, isInputEnabled = isInputEnabled)
    }
}

@Composable
fun ChatMessages(
    isReceiving: Boolean = false,
    sendMessage: (Boolean, String) -> Unit,
    modifier: Modifier,
    mentorName: String,
    mentorImgUrl: String,
    messageList: List<ChatMessage>,
    listState: LazyListState,
    onNavigateToClear: () -> Unit,
) {
    var isStartBoxVisible by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .background(color = Black100),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        state = listState
    ) {
        item {
            ChatHeader(messageList = messageList, mentorName = mentorName)

            if (messageList.isEmpty() && isStartBoxVisible) {
                ChatStartSelectionBox(
                    onStart = {
                        isStartBoxVisible = false
                        sendMessage(true, "대화해줘")
                    },
                    onNavigateBack = {
                        /* 채팅방 나가기 */
                    }
                )
            }
        }

        items(messageList) { message ->
            when (message.type) {
                MessageType.USER_REQUEST.name -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        message.message.content?.let {
                            ChatBubble(message = it)
                        }
                    }
                }

                MessageType.MENTOR_RESPONSE.name -> {
                    message.message.content?.let {
                        ChatWithProfileBubble(
                            message = it,
                            imgUrl = mentorImgUrl
                        )
                    }
                }
            }

            if (message.message.isFinish) { // 완료된 학습 방이 아니고, 마지막 메세지가 isFinish인 경우
                ChatFinishSelectionBox(onNavigateToClear)
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
fun ChatTextField(viewModel: ChatViewModel, isInputEnabled: Boolean) {
    var text by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(top = 16.dp, start = 32.dp, end = 32.dp, bottom = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .weight(1f)
                .background(color = Black100, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 16.dp, vertical = 10.dp),
            textStyle = TextStyle(
                fontFamily = robotoFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            enabled = isInputEnabled,
            interactionSource = interactionSource,
            decorationBox = @Composable { innerTextField ->
                val isFocused = interactionSource.collectIsFocusedAsState().value

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .imePadding(), verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isFocused && text.isEmpty()) {
                        Text(text = "대답하기", style = WizdumTheme.typography.body1, color = Black500)
                    }

                    innerTextField()
                }
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .background(
                    color = if (isInputEnabled) Green200 else Black200,
                    shape = RoundedCornerShape(10.dp)
                ) // enable color black200
                .clickable {
                    viewModel.setReceiving(true)
                    viewModel.sendMessage(name = "유니", lectureId = 1, message = text)
                    text = ""
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_btn_send_enable),
                contentDescription = "보내기"
            )
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

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun ChatScreenPreview() {
//    val chatlist = listOf(ChatMessage(
//        type = "MENTOR_RESPONSE",
//        lectureId = 1,
//        name = "스파르타 전사",
//        accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLjhYXjhYciLCJhdXRoIjpbXSwiaWQiOjE2LCJuYW1lIjoi44WF44WHIiwicGFzc3dvcmQiOiIiLCJpYXQiOjE3NDA3OTcxNjAsImV4cCI6MTc0MzM4OTE2MH0._hEeo0mCNgp2be2sbmuF7APAqGghZfdMncUxiCUzhpk",
//        message = MessageContent(
//            content = "이라도",
//            isFinish = true,
//            isLast = false
//        ),
//        isHide = false,
//        timestamp = "2025-03-01T11:24:56.642312938"
//    ))
//    WizdumTheme {
//        ChantContent(chatlist)
//    }
}