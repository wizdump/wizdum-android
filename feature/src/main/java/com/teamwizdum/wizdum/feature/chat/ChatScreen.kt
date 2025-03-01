package com.teamwizdum.wizdum.feature.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import com.teamwizdum.wizdum.designsystem.component.button.WizdumBorderButton
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black300
import com.teamwizdum.wizdum.designsystem.theme.Black50
import com.teamwizdum.wizdum.designsystem.theme.Black500
import com.teamwizdum.wizdum.designsystem.theme.Black700
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.designsystem.theme.robotoFontFamily
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.chat.info.MessageType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ChatScreen(viewModel: ChatViewModel = hiltViewModel()) {


    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    val message = viewModel.message.collectAsState().value

    ChantContent(message, viewModel)
}

@Composable
private fun ChantContent(message: List<ChatMessage>, viewModel: ChatViewModel) {
    val listState = rememberLazyListState()

    LaunchedEffect(message.size) {
        if (message.isNotEmpty()) {
            listState.animateScrollToItem(message.lastIndex)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackAppBar(title = "결심을 넘어 행동으로")
        HorizontalDivider(thickness = 1.dp, color = Black300)
        ChatMessages(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            messageList = message,
            listState = listState
        )
        ChatTextField(viewModel = viewModel)
    }
}

@Composable
fun ChatMessages(modifier: Modifier, messageList: List<ChatMessage>, listState: LazyListState) {
    LazyColumn(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .background(color = Black100),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        state = listState
    ) {
        item {
            // 신규 채팅이 아닌 경우 고려
            ChatHeader(mentorName = "스파르타")
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
                    message.message.content?.let { ChatWithProfileBubble(message = it) }
                }
            }

            if (message.message.isFinish) { // 완료된 학습 방이 아니고, 마지막 메세지가 isFinish인 경우
                ChatSelectionBox()
            }
        }
    }
}

@Composable
fun ChatBubble(message: String, modifier: Modifier = Modifier) {
    Text(
        text = message,
        modifier = modifier
            .background(
                color = Green200,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp)
            .widthIn(max = 200.dp),
        style = WizdumTheme.typography.body2,
        color = Color.White,
        softWrap = true
    )
}

@Composable
fun ChatWithProfileBubble(message: String) {
    Row {
        Box(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .background(color = Color.Green, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = "스파르타", style = WizdumTheme.typography.body2_semib)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(8.dp)
                    .widthIn(max = 200.dp),
                style = WizdumTheme.typography.body2,
                softWrap = true
            )
        }
    }
}

@Composable
fun ChatHeader(mentorName: String) {
    val today = LocalDate.now() // 특정 날짜 설정
    val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.KOREAN)
    val formattedDate = today.format(formatter)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = formattedDate, style = WizdumTheme.typography.body3)
        Text(text = "'$mentorName' 멘토 님이 입장하셨습니다.", style = WizdumTheme.typography.body3)
//        Spacer(modifier = Modifier.height(40.dp).width(30.dp).background(Color.Green))
    }
}

@Composable
private fun ChatSelectionBox() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
            .background(color = Black50, shape = RoundedCornerShape(20.dp))
            .border(width = 1.dp, color = Black200, shape = RoundedCornerShape(20.dp))
            .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_raising_hands),
            contentDescription = "학습 완료",
            modifier = Modifier
                .width(72.dp)
                .height(72.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "1강을 성공적으로 완료했어요!", style = WizdumTheme.typography.body3)
        Text(text = "강의를 마칠까요?", style = WizdumTheme.typography.body1_semib)
        Spacer(modifier = Modifier.height(16.dp))
        WizdumFilledButton(
            title = "강의를 완료할게요",
            textStyle = WizdumTheme.typography.body3_semib
        ) { /*강의 종료 -> 계속하기 화면 or 리워드 가기 화면 */ }
        Spacer(modifier = Modifier.height(8.dp))
        WizdumBorderButton(
            title = "더 물어볼래요",
            textStyle = WizdumTheme.typography.body3_semib
        ) {/* 사라져야 함*/ }
    }
}

@Composable
fun ChatTextField(viewModel: ChatViewModel) {
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
                .background(color = Black200, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 16.dp, vertical = 10.dp),
            textStyle = TextStyle(
                fontFamily = robotoFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
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
                    color = Black700,
                    shape = RoundedCornerShape(10.dp)
                ) // enable color black200
                .clickable {
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

@Preview
@Composable
fun ChatSelectionBoxPreview() {
    WizdumTheme {
        ChatSelectionBox()
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ChatBubblePreview() {
//    WizdumTheme {
//        ChatWithProfileBubble()
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun ChatTextFieldPreview() {
//    WizdumTheme {
//        ChatTextField()
//    }
//}

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