package com.teamwizdum.wizdum.feature.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.component.button.WizdumBorderButton
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black50
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R

@Composable
fun ChatStartSelectionBox(
    mentorName: String,
    orderSeq: Int,
    onStart: () -> Unit,
    onNavigateBack: () -> Unit,
) {
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
            painter = painterResource(id = R.drawable.img_speech_balloon),
            contentDescription = "학습 시작",
            modifier = Modifier
                .width(72.dp)
                .height(72.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "$mentorName 멘토님과", style = WizdumTheme.typography.body3)
        Text(text = "${orderSeq}강을 시작하시겠어요?", style = WizdumTheme.typography.body1_semib)
        Spacer(modifier = Modifier.height(16.dp))
        WizdumFilledButton(
            title = "네, 준비되었어요!",
            textStyle = WizdumTheme.typography.body2_semib
        ) {
            onStart()
        }
        Spacer(modifier = Modifier.height(8.dp))
        WizdumBorderButton(
            title = "나중에 다시 올게요",
            textStyle = WizdumTheme.typography.body2_semib
        ) {
            onNavigateBack()
        }
    }
}

@Composable
fun ChatFinishSelectionBox(
    orderSeq: Int,
    isLastLecture: Boolean,
    onGoing: () -> Unit,
    onNavigateToClear: () -> Unit,
    onNavigateToAllClear: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
        Text(text = "${orderSeq}강을 성공적으로 완료했어요!", style = WizdumTheme.typography.body3)
        Text(text = "강의를 마칠까요?", style = WizdumTheme.typography.body1_semib)
        Spacer(modifier = Modifier.height(16.dp))
        WizdumFilledButton(
            title = "강의를 완료할게요",
            textStyle = WizdumTheme.typography.body2_semib
        ) {
            if (isLastLecture) {
                onNavigateToAllClear()
            } else {
                onNavigateToClear()
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        WizdumBorderButton(
            title = "더 물어볼래요",
            textStyle = WizdumTheme.typography.body2_semib
        ) {
            onGoing()
        }
    }
}

@Preview
@Composable
fun ChatSelectionBox() {
    WizdumTheme {
        Column {
            ChatFinishSelectionBox(
                orderSeq = 1,
                isLastLecture = false,
                onGoing = {},
                onNavigateToClear = {},
                onNavigateToAllClear = {}
            )
            ChatStartSelectionBox(
                mentorName = "스파르타",
                orderSeq = 1,
                onStart = {},
                onNavigateBack = {}
            )

        }
    }
}