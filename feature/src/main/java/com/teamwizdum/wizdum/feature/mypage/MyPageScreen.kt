package com.teamwizdum.wizdum.feature.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwizdum.wizdum.data.model.response.UserResponse
import com.teamwizdum.wizdum.designsystem.component.appbar.TitleAppbar
import com.teamwizdum.wizdum.designsystem.component.dialog.ChoiceDialog
import com.teamwizdum.wizdum.designsystem.extension.noRippleClickable
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.mypage.MyPageViewModel.Companion.PRIVACY_POLICY
import com.teamwizdum.wizdum.feature.mypage.MyPageViewModel.Companion.TERMS_OF_SERVICE

@Composable
fun MyPageRoute(
    padding: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel(),
    restartMainActivity: () -> Unit,
    onNavigateToTerm: (String, String) -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.getUserInfo()
    }

    val userInfo = viewModel.userInfo.collectAsState().value
    var logoutDialogState by remember { mutableStateOf(false) }
    var withdrawDialogState by remember { mutableStateOf(false) }

    MyPageScreen(
        padding = padding,
        userInfo = userInfo,
        onLogout = { logoutDialogState = true },
        onWithdraw = { withdrawDialogState = true },
        onNavigateToTerm = onNavigateToTerm
    )

    ChoiceDialog(
        dialogState = logoutDialogState,
        title = "로그아웃 하시겠어요?",
        confirmTitle = "로그아웃",
        dismissTitle = "취소",
        onConfirmRequest = {
            viewModel.logout {
                restartMainActivity()
            }
        },
        onDismissRequest = {
            logoutDialogState = false
        }
    )

    ChoiceDialog(
        dialogState = withdrawDialogState,
        title = "정말 탈퇴하시겠어요?",
        subTitle = "계정 정보와 학습 기록이 영구 삭제됩니다.",
        confirmTitle = "탈퇴",
        dismissTitle = "취소",
        onConfirmRequest = {
            viewModel.withdraw {
                restartMainActivity()
            }
        },
        onDismissRequest = {
            withdrawDialogState = false
        }
    )
}

@Composable
private fun MyPageScreen(
    padding: PaddingValues,
    userInfo: UserResponse,
    onLogout: () -> Unit,
    onWithdraw: () -> Unit,
    onNavigateToTerm: (String, String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TitleAppbar(title = "마이페이지")
            Column(
                modifier = Modifier.padding(32.dp)
            ) {
                Row {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userInfo.profileImageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "유저 프로필 사진",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(color = Color.White)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = userInfo.name, style = WizdumTheme.typography.body1_semib)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = userInfo.email, style = WizdumTheme.typography.body3)
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Black100)
                    .padding(top = 32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MyPageWithIconSection(
                    title = "개인정보처리",
                    onClick = { onNavigateToTerm("개인정보처리", PRIVACY_POLICY) }
                )
                MyPageWithIconSection(
                    title = "이용약관",
                    onClick = { onNavigateToTerm("이용약관", TERMS_OF_SERVICE) }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                )
                MyPageSection(
                    title = "로그아웃",
                    onClick = { onLogout() }
                )
                MyPageSection(
                    title = "회원탈퇴",
                    onClick = { onWithdraw() }
                )
            }
        }
    }
}

@Composable
private fun MyPageWithIconSection(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp)
            .noRippleClickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = WizdumTheme.typography.body1)
        Icon(
            painter = painterResource(id = R.drawable.ic_btn_arrow_right),
            contentDescription = "취소",
        )
    }
}

@Composable
private fun MyPageSection(title: String, onClick: () -> Unit) {
    Text(
        text = title,
        style = WizdumTheme.typography.body1,
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 8.dp)
            .noRippleClickable { onClick() }
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun MyPageScreenPreview() {
    WizdumTheme {
        MyPageScreen(
            padding = PaddingValues(),
            userInfo = UserResponse(
                userId = 1,
                snsId = 1,
                email = "aaa@naver.com",
                name = "유니",
                password = ""
            ),
            onLogout = {},
            onWithdraw = {},
            onNavigateToTerm = { _, _ -> }
        )
    }
}