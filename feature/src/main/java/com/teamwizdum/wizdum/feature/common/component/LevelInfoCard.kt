package com.teamwizdum.wizdum.feature.common.component

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.theme.Black500
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.common.enums.Level

@Composable
fun LevelInfoCard(
    level: String,
    levelColor: Color = Black500,
    subTitleColor: Color = Black500,
) {
    val levelEnum = Level.fromString(level)
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LevelStarRating(level = level, levelColor = levelColor)
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
            color = subTitleColor
        )
    }
}

@Composable
fun LevelStarRating(level: String, levelColor: Color = Black500) {
    val levelEnum = Level.fromString(level)

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "레벨",
            style = WizdumTheme.typography.body2,
            color = levelColor
        )
        Spacer(modifier = Modifier.width(8.dp))

        repeat(levelEnum.starCount) {
            Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "별"
            )
        }
    }
}