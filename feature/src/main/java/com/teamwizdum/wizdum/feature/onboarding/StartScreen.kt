package com.teamwizdum.wizdum.feature.onboarding

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
import androidx.compose.foundation.shape.RoundedCornerShape
import com.teamwizdum.wizdum.feature.onboarding.info.Characters
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R

@Composable
fun StartScreen(onNavigateToKeyword: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 80.dp)
    ) {
        Spacer(modifier = Modifier.height(96.dp))
        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text(
                text = buildAnnotatedString {
                    append("과거와 미래가\n만나는 곳,\n당신만의 ")
                    withStyle(style = SpanStyle(color = WizdumTheme.colorScheme.primary)) {
                        append("AI 멘토링")
                    }
                },
                style = WizdumTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = buildAnnotatedString {
                    append("당신의 하루를 특별하게 만들어줄\n")
                    withStyle(style = SpanStyle(color = WizdumTheme.colorScheme.primary)) {
                        append("시간을 초월한")
                    }
                    append(" 멘토와의 대화")
                },
                style = WizdumTheme.typography.body1,
                color = Black600
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        WizdumAutoScroll()
        Spacer(modifier = Modifier.weight(1f))
        WizdumFilledButton(
            modifier = Modifier.padding(horizontal = 32.dp),
            title = "멘토 찾으러 떠나기",
        ) {
            onNavigateToKeyword()
        }
    }
}

@Composable
fun WizdumAutoScroll() {
  Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(34.dp)
  ) {
      for (character in Characters.entries) {
          WizdumCharacter(resId = character.resId, name = character.mentorName)
      }
  }
}

@Composable
fun WizdumCharacter(resId: Int, name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = name
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .background(
                    color = Black100,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = name, style = WizdumTheme.typography.body3_semib)
            Text(text = "멘토님", style = WizdumTheme.typography.body3)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WizdumCharacterPreview() {
    WizdumTheme {
        WizdumCharacter(
            resId = R.drawable.ic_wizdum_kant,
            name = "칸트"
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun StartScreenPreview() {
    WizdumTheme {
        StartScreen {}
    }
}