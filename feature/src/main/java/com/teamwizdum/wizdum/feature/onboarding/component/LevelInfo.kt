package com.teamwizdum.wizdum.feature.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.theme.Black500
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.onboarding.info.Level

@Composable
fun LevelInfo(level: String) {
    val levelEnum = Level.fromString(level)

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "레벨", style = WizdumTheme.typography.body2, color = Black500)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = levelEnum.rating,
            style = WizdumTheme.typography.body2,
            color = Black500
        )
        Box(
            modifier = Modifier
                .padding(6.dp)
                .height(2.dp)
                .width(2.dp)
                .background(color = Black500, shape = CircleShape)
        )
        Text(
            text = levelEnum.comment,
            style = WizdumTheme.typography.body2,
            color = Black500
        )
    }
}