package com.teamwizdum.wizdum.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun WizdumFilledButton(
    title: String,
    backgroundColor: Color = WizdumTheme.colorScheme.primary,
    textColor: Color = Color.White,
    textStyle: TextStyle = WizdumTheme.typography.h3_semib,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .background(color = backgroundColor, shape = RoundedCornerShape(10.dp))
        .clickable { onClick() }
    ) {
        Text(
            text = title,
            style = textStyle,
            color = textColor,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 40.dp, vertical = 16.dp)
        )
    }
}

@Preview
@Composable
fun WizdumFilledButtonPreview() {
    WizdumTheme {
        WizdumFilledButton("확인") {}
    }
}