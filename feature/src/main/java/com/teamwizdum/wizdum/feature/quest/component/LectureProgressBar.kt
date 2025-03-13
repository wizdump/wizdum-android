package com.teamwizdum.wizdum.feature.quest.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun LectureProgressBar(modifier: Modifier = Modifier, progress: Float) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(
                color = Color(0x80333333),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(color = Black600)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(color = Black600)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = progress)
                .background(
                    color = WizdumTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
        )
    }
}

@Composable
@Preview
fun LectureProgressBarPreview() {
    WizdumTheme {
        LectureProgressBar(progress = 0.33f)
    }
}