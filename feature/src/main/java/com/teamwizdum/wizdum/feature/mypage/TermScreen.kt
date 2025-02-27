package com.teamwizdum.wizdum.feature.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun TermScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackAppBar(title = "개인정보처리", onNavigateBack = {})

            // 내용 첨부
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun TermScreenPreview() {
    WizdumTheme {
        TermScreen()
    }
}