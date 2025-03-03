package com.teamwizdum.wizdum.designsystem.component.badge

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun TextBadge(
    title: String,
    backgroundColor: Color = WizdumTheme.colorScheme.primary,
    borderColor: Color = WizdumTheme.colorScheme.primary,
    textColor: Color = WizdumTheme.colorScheme.secondary,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = title,
            style = WizdumTheme.typography.body3,
            color = textColor
        )
    }
}

@Composable
fun TextWithIconBadge(
    title: String,
    resId: Int,
    backgroundColor: Color = WizdumTheme.colorScheme.primary,
    borderColor: Color = WizdumTheme.colorScheme.primary,
    textColor: Color = WizdumTheme.colorScheme.secondary,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = resId), contentDescription = "아이콘")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                style = WizdumTheme.typography.body3,
                color = textColor
            )
        }
    }
}