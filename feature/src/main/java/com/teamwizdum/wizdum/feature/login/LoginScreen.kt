package com.teamwizdum.wizdum.feature.login

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    LoginContent(viewModel)
}

@Composable
private fun LoginContent(viewModel: LoginViewModel) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        BackAppBar()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = buildAnnotatedString {
                    append("로그인을 통해\n")
                    withStyle(style = SpanStyle(color = WizdumTheme.colorScheme.primary)) {
                        append("멘토님")
                    }
                    append("과 함께해요")
                },
                style = WizdumTheme.typography.h2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "맞춤형 멘토추천과 학습기록 저장이 가능해요!", style = WizdumTheme.typography.body1)
            Spacer(modifier = Modifier.height(32.dp))
            kakaoLoginButton(context = context, onKakaoSuccess = { accessToken ->
                viewModel.login(accessToken)
            })
        }
    }
}

@Composable
private fun kakaoLoginButton(context: Context, onKakaoSuccess: (String) -> Unit) {
    WizdumFilledButton(
        title = "카카오로 로그인",
        backgroundColor = Color(0xFFFFDC00),
        textColor = Color(0xFF1E1E1E),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            KaKaoLoginManager.login(
                context = context,
                onSuccess = { accessToken ->
                    onKakaoSuccess(accessToken)
                },
                onFailed = {

                }
            )
        }
    )
}

//@Preview(showBackground = true, widthDp = 360, heightDp = 800)
//@Composable
//fun LoginScreenPreview() {
//    WizdumTheme {
//        LoginContent()
//    }
//}