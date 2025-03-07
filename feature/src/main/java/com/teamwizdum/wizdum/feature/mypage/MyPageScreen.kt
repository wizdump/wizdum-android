package com.teamwizdum.wizdum.feature.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.data.model.response.UserResponse
import com.teamwizdum.wizdum.designsystem.component.appbar.TitleAppbar
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R

@Composable
fun MyPageScreen(viewModel: MyPageViewModel) {

    LaunchedEffect(Unit) {
        viewModel.getUserInfo()
    }

    val userInfo = viewModel.userInfo.collectAsState().value

    MyPageContent(userInfo = userInfo, onNavigateToTerm = {})
}

@Composable
private fun MyPageContent(userInfo: UserResponse, onNavigateToTerm: () -> Unit) {
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
                Row() {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .background(color = Color.Green, shape = CircleShape)
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
                MyPageWithIconSection("개인정보처리") {
                    onNavigateToTerm()
                }
                MyPageWithIconSection("이용약관") {
                    onNavigateToTerm()
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                )
                MyPageSection(title = "로그아웃", onClick = {

                })
                MyPageSection(title = "회원탈퇴", onClick = {

                })
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
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
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
            .clickable {
                onClick()
            }
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun MyPageScreenPreview() {
    WizdumTheme {
        MyPageContent(
            UserResponse(
                userId = 1,
                snsId = 1,
                email = "aaa@naver.com",
                name = "유니",
                password = ""
            )
        ) {}
    }
}