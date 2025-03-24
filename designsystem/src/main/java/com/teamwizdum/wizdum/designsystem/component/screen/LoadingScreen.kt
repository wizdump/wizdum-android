package com.teamwizdum.wizdum.designsystem.component.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen() {
    var showProgressBar by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        showProgressBar = true
    }

    if (showProgressBar) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = Green200
            )
        }
    }
}

@Preview(widthDp = 380, heightDp = 800)
@Composable
fun LoadingScreenPreview() {
    WizdumTheme {
        LoadingScreen()
    }
}