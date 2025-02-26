package com.teamwizdum.wizdum.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun TextBadge(
    title: String,
    bodyColor: Color = Color.Green,
    textColor: Color = Color.Black,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(color = bodyColor, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = title, style = WizdumTheme.typography.body3)
    }
}

@Composable
fun IconBadge() {
}