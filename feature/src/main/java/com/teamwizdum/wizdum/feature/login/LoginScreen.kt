package com.teamwizdum.wizdum.feature.login

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val context = LocalContext.current

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            kakaoLoginButton(context = context, onKakaoSuccess = { accessToken ->
                viewModel.login(accessToken)
            })
        }
    }
}

@Composable
private fun kakaoLoginButton(context: Context, onKakaoSuccess: (String) -> Unit) {
    Box(modifier = Modifier
        .background(color = Color.Black)
        .fillMaxWidth()
        .background(color = Color.Yellow)
        .clickable {
            KaKaoLoginManager.login(
                context = context,
                onSuccess = { accessToken ->
                    onKakaoSuccess(accessToken)
                },
                onFailed = {

                })
        }) {
        Text(
            text = "카카오로 시작하기", modifier = Modifier
                .align(Alignment.Center)
                .padding(20.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun LoginScreenPreview() {
    WizdumTheme {
        LoginScreen()
    }
}