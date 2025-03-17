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
import com.teamwizdum.wizdum.data.model.response.KeywordResponse
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black500
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.common.base.UiState

@Composable
fun KeywordSelectionRoute(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToMentor: (Int) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getKeyword()
    }

    val uiState = viewModel.keywords.collectAsState().value

    when (uiState) {
        is UiState.Loading -> {}
        is UiState.Success -> {
            KeywordSelectionScreen(
                keywords = uiState.data,
                onNavigateBack = onNavigateBack,
                onNavigateToMentor = { onNavigateToMentor(it) }
            )
        }
        is UiState.Failed -> {}
    }
}

@Composable
private fun KeywordSelectionScreen(
    keywords: List<KeywordResponse>,
    onNavigateBack: () -> Unit,
    onNavigateToMentor: (Int) -> Unit,
) {
    val selectedIndex = remember { mutableStateOf(-1) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            BackAppBar(onNavigateBack = onNavigateBack)
            Column(modifier = Modifier.padding(top = 32.dp, start = 32.dp, end = 32.dp)) {
                Text(text = "어떠한 나로 성장하고 싶나요?", style = WizdumTheme.typography.h2)
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
                    items(keywords.size, key = { it }) { index ->
                        KeywordCard(
                            title = keywords[index].value,
                            description = keywords[index].description,
                            imageUrl = keywords[index].fileUrl,
                            isSelected = selectedIndex.value == index,
                            onClick = {
                                selectedIndex.value =
                                    if (selectedIndex.value == index) -1 else index
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(200.dp))
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
                title = "멘토 추천받기",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp, start = 32.dp, end = 32.dp)
                    .align(Alignment.BottomCenter)
            ) {
                onNavigateToMentor(keywords[selectedIndex.value].keywordId)
            }
    }
}

@Composable
private fun KeywordCard(
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

@Preview
@Composable
fun KeywordCardPreview() {
    Column {
        KeywordCard(
            title = "도전적인",
            imageUrl = "",
            description = "새로운 목표에 도전하고, 극복하는 힘을 기르고 싶어요",
            isSelected = true,
            onClick = {})
        Spacer(modifier = Modifier.height(50.dp))
        KeywordCard(
            title = "도전적인",
            imageUrl = "",
            description = "새로운 목표에 도전하고, 극복하는 힘을 기르고 싶어요",
            isSelected = false,
            onClick = {})
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun KeywordSelectionScreenPreview() {
    WizdumTheme {
        val keywordList = listOf(
            KeywordResponse(
                value = "도전적인",
                description = "새로운 목표에 도전하고, 극복하는 힘을 기르고 싶어요"
            ),
            KeywordResponse(
                value = "창의적인",
                description = "틀에 얽매이지 않고, 새로운 시각과 생각을 키우고 싶어요"
            ),
            KeywordResponse(
                value = "사색적인",
                description = "깊이 있는 사고와 철학적 통찰로 삶을 바라보고 싶어요"
            ),
            KeywordResponse(
                value = "분석적인",
                description = "논리적으로 사고하고, 문제 해결 능력을 키우고 싶어요"
            ),
            KeywordResponse(
                value = "건강한",
                description = "신체적·정신적 균형을 통해 지속적인 성장을 이루고 싶어요"
            ),
        )
        KeywordSelectionScreen(
            keywords = keywordList,
            onNavigateBack = {},
            onNavigateToMentor = {}
        )
    }
}