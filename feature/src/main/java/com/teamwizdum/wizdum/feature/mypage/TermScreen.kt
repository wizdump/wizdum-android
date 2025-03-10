package com.teamwizdum.wizdum.feature.mypage

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun TermScreen(title: String, webUrl: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        BackAppBar(
            modifier = Modifier.background(color = Color.White),
            title = title,
            onNavigateBack = {})

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                return@AndroidView WebView(context).apply {
                    webViewClient = WebViewClient()
                }
            },
            update = {
                it.loadUrl(webUrl)
            },
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun TermScreenPreview() {
    WizdumTheme {
        TermScreen(title = "개인정보처리방침", webUrl = "")
    }
}