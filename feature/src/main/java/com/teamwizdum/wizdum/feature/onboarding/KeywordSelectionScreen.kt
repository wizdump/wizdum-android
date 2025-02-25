package com.teamwizdum.wizdum.feature.onboarding

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.designsystem.component.BasicButton
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun KeywordSelectionScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    clickNext: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getKeyword()
    }

    val keywords = viewModel.keywords.collectAsState().value

    Box(modifier = Modifier.padding(top = 24.dp, start = 32.dp, end = 32.dp)) {
        Column {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .background(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = "어떤 하루를 보내고 싶나요?", style = WizdumTheme.typography.h2)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "1개 선택 가능", style = WizdumTheme.typography.body1)
            Spacer(modifier = Modifier.height(32.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(keywords) { keyword ->
                    KeywordCard(title = keyword.value)
                }
            }
        }
        BasicButton(
            title = "다음",
            bodyColor = Color.Green,
            textColor = Color.Black,
            modifier = Modifier.padding(bottom = 80.dp).align(Alignment.BottomCenter)
        ) {
            clickNext()
        }
    }
}

@Composable
private fun KeywordCard(title: String) {
    WizdumTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
                .padding(top = 24.dp, bottom = 24.dp, start = 32.dp, end = 32.dp),
            contentAlignment = Alignment.Center

        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Black)
                        .width(72.dp)
                        .height(72.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = title,
                    style = WizdumTheme.typography.h3,
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun KeywordSelectionScreenPreview() {
    WizdumTheme {
        KeywordSelectionScreen {}
    }
}