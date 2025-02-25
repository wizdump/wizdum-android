package com.teamwizdum.wizdum.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun BasicButton(
    title: String,
    bodyColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .background(color = bodyColor, shape = RoundedCornerShape(10.dp))
        .clickable { onClick() }
    ) {
        Text(
            text = title,
            style = WizdumTheme.typography.h3_semib,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 40.dp, vertical = 16.dp)
        )
    }
}