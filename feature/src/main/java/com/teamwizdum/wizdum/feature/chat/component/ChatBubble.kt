package com.teamwizdum.wizdum.feature.chat.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwizdum.wizdum.designsystem.theme.Black300
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChatBubble(message: String, modifier: Modifier = Modifier) {
    Text(
        text = message,
        modifier = modifier
            .background(
                color = Green200,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp)
            .widthIn(max = 200.dp),
        style = WizdumTheme.typography.body2,
        color = Color.White,
        softWrap = true
    )
}

@Composable
fun ChatWithProfileBubble(message: String, name: String, imgUrl: String) {
    Row {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgUrl)
                .crossfade(true)
                .build(),
            contentDescription = "멘토 프로필 사진",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color = Color.White)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = name, style = WizdumTheme.typography.body2_semib)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(8.dp)
                    .widthIn(max = 200.dp),
                style = WizdumTheme.typography.body2,
                softWrap = true
            )
        }
    }
}

@Composable
fun TypingIndicatorBubble(name: String, imgUrl: String) {
    Row {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgUrl)
                .crossfade(true)
                .build(),
            contentDescription = "멘토 프로필 사진",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color = Color.White)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = name, style = WizdumTheme.typography.body2_semib)
            Spacer(modifier = Modifier.height(8.dp))
            TypingIndicator()
        }
    }
}

@Composable
fun TypingIndicator() {
    val dots = List(3) { remember { Animatable(0.3f) } }

    LaunchedEffect(Unit) {
        while (true) {
            dots.forEach { animatable ->
                launch {
                    animatable.animateTo(1f, animationSpec = tween(300))
                    animatable.animateTo(0.3f, animationSpec = tween(300))
                }
                delay(200)
            }
        }
    }

    Row(
        modifier = Modifier
            .width(50.dp)
            .height(24.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        dots.forEach { animatable ->
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .graphicsLayer { this.alpha = animatable.value }
                    .background(color = Black300, shape = CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatBubblePreview() {
    WizdumTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ChatBubble(message = "안녕하세요!")
            ChatWithProfileBubble(message = "반갑다.", name = "스파르타", imgUrl = "")
        }
    }
}