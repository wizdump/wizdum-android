package com.teamwizdum.wizdum.feature.chat.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ChatHeader(messageList: List<ChatMessage>, mentorName: String) {
    var date = ""
    if (messageList.isEmpty()) {
        val today = LocalDate.now() // 특정 날짜 설정
        val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.KOREAN)
        date = today.format(formatter)
    } else {
        date = messageList.first().timestamp
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = date, style = WizdumTheme.typography.body3)
        Text(text = "'$mentorName' 멘토 님이 입장하셨습니다.", style = WizdumTheme.typography.body3)
    }
}