package com.teamwizdum.wizdum.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.designsystem.theme.robotoFontFamily

@Composable
fun ChatScreen(viewModel: ChatViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    val message = viewModel.message.collectAsState().value

    Scaffold(
        topBar = { ChatTopBar() },
        bottomBar = { ChatTextField(viewModel = viewModel) }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            ChatMessages(
                modifier = Modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar() {
    CenterAlignedTopAppBar(
        title = { Text("1강 : 결심을 넘어 행동으로", style = WizdumTheme.typography.body1_semib) },
        navigationIcon = {
            Icon(
                painter = painterResource(id = com.teamwizdum.wizdum.designsystem.R.drawable.ic_btn_arrow_back),
                contentDescription = "뒤로가기",
                modifier = Modifier
                    .clickable {

                    }
                    .padding(16.dp)
            )
        },
        modifier = Modifier.shadow(1.dp),
        windowInsets = WindowInsets(0.dp)
    )
}

@Composable
fun ChatMessages(modifier: Modifier) {
    // 최신 메세지가 아래에서부터 쌓이도록 설정
    LazyColumn(modifier = Modifier.padding(top = 16.dp, start = 12.dp, end = 12.dp)) {
        items(10) {
            ChatWithProfileBubble()
        }
    }
}

@Composable
fun ChatBubble(message: String) {
    // 채팅 Id? or isSentByMe와 같은 상태에 따라 나-멘토 체팅 구분, 정렬
    Text(
        text = "반갑다 유니. 반갑다 유니. 반갑다 유니",
        modifier = Modifier
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp)
            .widthIn(max = 130.dp),
        style = WizdumTheme.typography.body2,
        softWrap = true
    )
}

@Composable
fun ChatWithProfileBubble() {
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
                text = "반갑다 유니. 반갑다 유니. 반갑다 유니",
                modifier = Modifier
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(8.dp)
                    .widthIn(max = 130.dp),
                style = WizdumTheme.typography.body2,
                softWrap = true
            )
        }
    }
}

@Composable
fun ChatStartDate() {
    Row() {
        Text(text = "2025년 2월 23일")
        Text(text = "'스파르타' 멘토 님이 입장하셨습니다.")
    }
}

@Composable
fun ChatTextField(viewModel: ChatViewModel) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 32.dp, end = 32.dp, bottom = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(fontSize = 14.sp, fontFamily = robotoFontFamily),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            placeholder = { Text("대답하기", style = WizdumTheme.typography.body1) }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .background(color = Color.Black)
                .clickable {
                    viewModel.sendMessage(1, text)
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatBubblePreview() {
    WizdumTheme {
        ChatWithProfileBubble()
    }
}

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
    WizdumTheme {
        ChatScreen()
    }
}