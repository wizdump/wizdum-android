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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.teamwizdum.wizdum.designsystem.theme.Black700
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R

@Composable
fun KeywordSelectionScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onNavigateNext: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getKeyword()
    }

    val keywords = viewModel.keywords.collectAsState().value

    KeywordContent(keywords = keywords, onNavigateNext = onNavigateNext)
}

@Composable
private fun KeywordContent(
    keywords: List<KeywordResponse>,
    onNavigateNext: () -> Unit,
) {
    val selectedIndex = remember { mutableStateOf(-1) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            BackAppBar()
            Column(modifier = Modifier.padding(top = 32.dp, start = 32.dp, end = 32.dp)) {
                Text(text = "어떤 하루를 보내고 싶나요?", style = WizdumTheme.typography.h2)
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

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(keywords.size, key = { it }) { index ->
                        KeywordCard(
                            title = keywords[index].value,
                            imageUrl = "",
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
                onNavigateNext()
            }
    }
}

@Composable
private fun KeywordCard(
    title: String,
    imageUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.White)
            .border(
                width = 2.dp,
                color = if (isSelected) WizdumTheme.colorScheme.primary else Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "키워드 이미지",
                modifier = Modifier
                    .width(72.dp)
                    .height(72.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSelected)
                    Image(
                        painter = painterResource(id = R.drawable.ic_checked),
                        contentDescription = "체크 아이콘",
                    )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = title,
                    style = if (isSelected) WizdumTheme.typography.h3_semib else WizdumTheme.typography.h3,
                    color = if (isSelected) Black700 else Black600,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun KeywordCardPreview() {
    Column {
        KeywordCard(title = "도전적인", imageUrl = "", isSelected = true, onClick = {})
        Spacer(modifier = Modifier.height(50.dp))
        KeywordCard(title = "도전적인", imageUrl = "", isSelected = false, onClick = {})
    }

}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun KeywordSelectionScreenPreview() {
    WizdumTheme {
        val keywordList = listOf(
            KeywordResponse(value = "도전적인"),
            KeywordResponse(value = "성취 지향적인"),
            KeywordResponse(value = "감성적인"),
            KeywordResponse(value = "창의적인"),
            KeywordResponse(value = "사색적인"),
            KeywordResponse(value = "탐구적인"),
            KeywordResponse(value = "논리적인"),
            KeywordResponse(value = "건강한")
        )
        KeywordContent(keywords = keywordList) {}
    }
}