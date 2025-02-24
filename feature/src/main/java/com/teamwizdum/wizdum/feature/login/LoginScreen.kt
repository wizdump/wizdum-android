package com.teamwizdum.wizdum.feature.login

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.teamwizdum.wizdum.designsystem.theme.WizdumandroidTheme

@Composable
fun LoginScreen() {
    Box {
        Text(text = "로그인")
    }
}

@Preview(showBackground = true, widthDp = 300, heightDp = 700)
@Composable
fun LoginScreenPreview() {
    WizdumandroidTheme {
        LoginScreen()
    }
}