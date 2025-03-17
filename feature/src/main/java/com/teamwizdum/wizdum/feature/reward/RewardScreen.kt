package com.teamwizdum.wizdum.feature.reward

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwizdum.wizdum.data.model.response.RewardResponse
import com.teamwizdum.wizdum.designsystem.component.appbar.CloseAppBar
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black300
import com.teamwizdum.wizdum.designsystem.theme.Black400
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.common.base.UiState
import com.teamwizdum.wizdum.feature.common.extensions.formatBasicDateTime

@Composable
fun RewardRoute(
    viewModel: RewardViewModel = hiltViewModel(),
    classId: Int,
    onNavigateToHome: () -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.getReward(classId)
    }

    val uiState = viewModel.rewardInfo.collectAsState().value

    when (uiState) {
        is UiState.Loading -> {}
        is UiState.Success -> {
            RewardScreen(
                rewardInfo = uiState.data,
                onNavigateToHome = onNavigateToHome
            )
        }
        is UiState.Failed -> {}
    }
}

@Composable
private fun RewardScreen(
    rewardInfo: RewardResponse,
    onNavigateToHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        CloseAppBar(onClose = {
            onNavigateToHome()
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 176.dp, start = 32.dp, end = 32.dp, bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    append("${rewardInfo.mentoName} 멘토님이 ")
                    withStyle(
                        style = SpanStyle(
                            color = WizdumTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(rewardInfo.userName)
                    }
                    append("님에게")
                },
                style = WizdumTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "리워드를 수여하셨습니다!", style = WizdumTheme.typography.h2)
            Spacer(modifier = Modifier.height(16.dp))
            RewardCard(rewardInfo)
            Spacer(modifier = Modifier.height(48.dp))
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .background(color = Black200, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_btn_save),
                        contentDescription = "공유하기"
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                WizdumFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = "나의 승리 공유하기",
                ) {
                    // TODO : 공유로 넘어가면 창 종료, 복귀 시 메인 화면으로 이동
                }
            }
        }
    }
}

@Composable
private fun RewardCard(rewardInfo: RewardResponse) {
    Box(
        modifier = Modifier
            .width(230.dp)
            .height(300.dp)
            .background(color = Black300, shape = RoundedCornerShape(20.dp))

    ) {
        // 카드 배경 이미지
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(rewardInfo.filePath)
                .crossfade(true)
                .build(),
            contentDescription = "멘토 프로필 사진",
            modifier = Modifier
                .width(230.dp)
                .height(300.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = Black100)
        )
        Box(
            modifier = Modifier
                .width(230.dp)
                .height(300.dp)
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(29.dp)
                    .height(32.dp)
                    .background(
                        color = WizdumTheme.colorScheme.primary,
                        shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                    )
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_certificated),
                    contentDescription = "인증"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append(rewardInfo.mentoName)
                        }
                        append(" 멘토님께")
                    },
                    style = WizdumTheme.typography.body3,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = rewardInfo.summary,
                    style = WizdumTheme.typography.h3_semib,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatBasicDateTime(rewardInfo.createdAt),
                    style = WizdumTheme.typography.body3,
                    color = Black400
                )
            }
        }
    }
}

@Preview
@Composable
fun RewardScreenPreview() {
    WizdumTheme {
        RewardScreen(
            rewardInfo = RewardResponse(),
            onNavigateToHome = {}
        )
    }
}