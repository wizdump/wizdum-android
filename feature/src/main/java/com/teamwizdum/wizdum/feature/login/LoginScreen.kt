package com.teamwizdum.wizdum.feature.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    classId: Int,
    onNavigateToLecture: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    LaunchedEffect(classId) {
        viewModel.classId = classId
    }

    LoginScreen(
        onLoginClick = { accessToken ->
            viewModel.login(
                accessToken = accessToken,
                moveToLecture = { onNavigateToLecture() },
                moveToHome = { onNavigateToHome() }
            )
        }
    )
}

@Composable
private fun LoginScreen(
    onLoginClick: (String) -> Unit,
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        BackAppBar()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_wizdum),
                contentDescription = "위즈덤 로고"
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = buildAnnotatedString {
                    append("로그인을 통해\n")
                    withStyle(style = SpanStyle(color = Green200)) {
                        append("멘토님")
                    }
                    append("과 함께해요")
                },
                style = WizdumTheme.typography.h2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "맞춤형 멘토추천과 학습기록 저장이 가능해요!",
                style = WizdumTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(32.dp))
            KakaoLoginButton(
                context = context,
                onKakaoSuccess = { accessToken ->
                    onLoginClick(accessToken)
                }
            )
        }
    }
}

@Composable
fun KakaoLoginButton(context: Context, onKakaoSuccess: (String) -> Unit) {
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

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun LoginScreenPreview() {
    WizdumTheme {
        LoginScreen {}
    }
}