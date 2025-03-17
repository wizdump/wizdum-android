package com.teamwizdum.wizdum.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwizdum.wizdum.data.model.response.InterestResponse
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black500
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.common.base.UiState

@Composable
fun InterestSelectionRoute(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToLevel: (Int) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getInterest()
    }

    val uiState = viewModel.interests.collectAsState().value

    when (uiState) {
        is UiState.Loading -> {}
        is UiState.Success -> {
            InterestSelectionScreen(
                interests = uiState.data,
                onNavigateBack = onNavigateBack,
                onNavigateToLevel = { onNavigateToLevel(it) }
            )
        }
        is UiState.Failed  -> {}
    }
}

@Composable
fun InterestSelectionScreen(
    interests: List<InterestResponse>,
    onNavigateBack: () -> Unit,
    onNavigateToLevel: (Int) -> Unit,
) {
    val selectedIndex = remember { mutableStateOf(-1) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            BackAppBar(onNavigateBack = onNavigateBack)
            Column(modifier = Modifier.padding(top = 32.dp, start = 32.dp, end = 32.dp)) {
                Text(text = "당신이 현재 가장 관심 있는 분야는 무엇인가요", style = WizdumTheme.typography.h2)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = WizdumTheme.colorScheme.primary)) {
                            append("1개")
                        }
                        append(" 선택 가능")
                    },
                    style = WizdumTheme.typography.body1,
                    color = Black500
                )
                Spacer(modifier = Modifier.height(32.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(interests.size, key = { it }) { index ->
                        InterestCard(
                            title = interests[index].value,
                            description = interests[index].description,
                            imageUrl = interests[index].fileUrl,
                            isSelected = selectedIndex.value == index,
                            onClick = {
                                selectedIndex.value =
                                    if (selectedIndex.value == index) -1 else index
                            }
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Transparent, Black600),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 1000f)
                    )
                )
                .align(Alignment.BottomCenter)
        )

        if (selectedIndex.value != -1)
            WizdumFilledButton(
                title = "다음",
                modifier = Modifier
                    .padding(bottom = 80.dp, start = 32.dp, end = 32.dp)
                    .align(Alignment.BottomCenter)
            ) {
                onNavigateToLevel(interests[selectedIndex.value].interestId)
            }
    }
}

@Composable
private fun InterestCard(
    title: String,
    description: String,
    imageUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .border(
                width = 2.dp,
                color = if (isSelected) WizdumTheme.colorScheme.primary else Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "키워드 이미지",
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = if (isSelected) WizdumTheme.typography.body1_semib else WizdumTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(4.dp))
                if (isSelected)
                    Image(
                        painter = painterResource(id = R.drawable.ic_checked),
                        contentDescription = "체크 아이콘",
                    )
            }
            Text(
                text = description,
                style = WizdumTheme.typography.body3,
                color = Black600,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun InterestSelectionScreenPreview() {
    WizdumTheme {
        InterestSelectionScreen(
            interests = listOf(
                InterestResponse(
                    interestId = 1,
                    value = "창의성과 혁신",
                    description = "기존의 틀을 깨고 새로운 아이디어를 찾고 싶어요.",
                    fileUrl = "https://kr.object.ncloudstorage.com/wizdump/category_interest/Group_303.png"
                ),
                InterestResponse(
                    interestId = 2,
                    value = "리더십과 경영철학",
                    description = "효과적인 리더십과 조직 운영을 배우고 싶어요.",
                    fileUrl = "https://kr.object.ncloudstorage.com/wizdump/category_interest/Group_304.png"
                ),
                InterestResponse(
                    interestId = 3,
                    value = "논리적 사고 & 문제 해결",
                    description = "복잡한 문제를 분석하고 최적의 해결책을 찾고 싶어요.",
                    fileUrl = "https://kr.object.ncloudstorage.com/wizdump/category_interest/Group_305.png"
                ),
                InterestResponse(
                    interestId = 4,
                    value = "역사적 인물들의 철학",
                    description = "위인들의 지혜를 통해 삶을 더 깊이 이해하고 싶어요.",
                    fileUrl = "https://kr.object.ncloudstorage.com/wizdump/category_interest/Group_306.png"
                ),
                InterestResponse(
                    interestId = 5,
                    value = "목표 설정 & 습관 형성",
                    description = "나에게 맞는 루틴을 만들고 꾸준히 실천하고 싶어요.",
                    fileUrl = "https://kr.object.ncloudstorage.com/wizdump/category_interest/Group_307.png"
                ),
                InterestResponse(
                    interestId = 6,
                    value = "웰빙과 균형",
                    description = "정신/신체적 건강을 유지하며 지속적으로 성장하고 싶어요.",
                    fileUrl = "https://kr.object.ncloudstorage.com/wizdump/category_interest/Group_307_1.png"
                )
            ),
            onNavigateBack = {},
            onNavigateToLevel = {}
        )
    }
}