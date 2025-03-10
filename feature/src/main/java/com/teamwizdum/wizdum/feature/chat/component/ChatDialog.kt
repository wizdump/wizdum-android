package com.teamwizdum.wizdum.feature.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.component.button.WizdumBorderButton
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black50
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R

@Composable
fun ChatDialog() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.White)
    ) {
        Column {
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
                    painter = painterResource(id = R.drawable.img_stop),
                    contentDescription = "잠깐",
                    modifier = Modifier
                        .width(72.dp)
                        .height(72.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "앗, 잠시만요!", style = WizdumTheme.typography.body3_semib)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "강의를 그만두시나요?", style = WizdumTheme.typography.body1_semib)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "지금 나가면 진행 중인 강의가 중단됩니다.\n멘토의 조언을 끝까지 듣지 않고 떠나시겠습니까?",
                    style = WizdumTheme.typography.body3,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                WizdumFilledButton(
                    title = "계속 진행하기",
                    textStyle = WizdumTheme.typography.body2_semib
                ) {
                    // 다이얼로그만 닫기
                }
                Spacer(modifier = Modifier.height(8.dp))
                WizdumBorderButton(
                    title = "정말 나가기",
                    textColor = WizdumTheme.colorScheme.error,
                    textStyle = WizdumTheme.typography.body2_semib,
                    borderColor = WizdumTheme.colorScheme.error
                ) {
                    // 나가기
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatDialogPreview() {
    WizdumTheme {
        ChatDialog()
    }
}