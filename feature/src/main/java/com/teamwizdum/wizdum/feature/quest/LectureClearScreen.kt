package com.teamwizdum.wizdum.feature.quest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.quest.navigation.argument.LectureClearArgument

@Composable
fun LectureClearScreen(
    lectureInfo: LectureClearArgument,
    onNavigateToLecture: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 176.dp, start = 32.dp, end = 32.dp, bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_checked),
                contentDescription = "체크",
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${lectureInfo.orderSeq}강 클리어!",
                style = WizdumTheme.typography.h2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "'${lectureInfo.lectureName}'를\n성공적으로 완료했어요.",
                style = WizdumTheme.typography.body1,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))
            MentorCommentBox(
                lectureInfo.mentorName,
                lectureInfo.encouragement
            )
            Spacer(modifier = Modifier.weight(1f))

            WizdumFilledButton(
                modifier = Modifier.fillMaxWidth(),
                title = "계속하기"
            ) {
                onNavigateToLecture(lectureInfo.classId)
            }
        }
    }
}

@Composable
fun MentorCommentBox(mentorName: String, comment: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Black100, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Column {
            Text(
                text = "\uD83D\uDCAC $mentorName 멘토님의 격려 한줄",
                style = WizdumTheme.typography.body2_semib
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Spacer(modifier = Modifier.width(18.dp))
                Text(
                    text = comment,
                    style = WizdumTheme.typography.body2
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun LectureClearScreenPreview() {
    WizdumTheme {
        LectureClearScreen(
            lectureInfo = LectureClearArgument(
                mentorName = "스파르타",
                orderSeq = 1,
                lectureName = "결심을 넘어 행동으로",
                encouragement = "좋다. 하지만 이것은 시작에 불과하다.\n다음단계에서도 너는 더 강해져야 한다."
            )
        ) {}
    }
}