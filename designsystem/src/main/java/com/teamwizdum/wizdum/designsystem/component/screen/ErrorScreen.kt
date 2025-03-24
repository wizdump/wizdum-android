package com.teamwizdum.wizdum.designsystem.component.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.R
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.Black700
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    retry: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_network),
            contentDescription = "네트워크 오류"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "인터넷 연결이 불안정해요",
            style = WizdumTheme.typography.body1_semib
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Wi-Fi나 데이터 연결 상태를 확인하고\n다시 시도해주세요.",
            style = WizdumTheme.typography.body3,
            color = Black600,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        WizdumFilledButton(
            title = "재시도",
            textColor = Color.White,
            backgroundColor = Black700,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                retry()
            }
        )
    }
}

@Preview
@Composable
fun ErrorScreen() {
    WizdumTheme {
        ErrorScreen {}
    }
}
