package com.teamwizdum.wizdum.designsystem.component.appbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun BasicAppBar(
    modifier: Modifier = Modifier,
    height: Dp = 60.dp,
    startIcon: @Composable () -> Unit = {},
    title: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            startIcon()
            Spacer(modifier = modifier.weight(1f))
            actions()
        }
        title()
    }
}

@Preview(showBackground = true)
@Composable
fun BasicAppBarPreview() {
    WizdumTheme {
        BasicAppBar(
            title = { Text("title") }
        )
    }
}
