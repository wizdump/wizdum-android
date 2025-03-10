package com.teamwizdum.wizdum.feature.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black500
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.designsystem.theme.robotoFontFamily
import com.teamwizdum.wizdum.feature.R

@Composable
fun ChatTextField(isInputEnabled: Boolean, sendMessage: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(top = 16.dp, start = 32.dp, end = 32.dp, bottom = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .weight(1f)
                .background(color = Black100, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 16.dp, vertical = 10.dp),
            textStyle = TextStyle(
                fontFamily = robotoFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            enabled = isInputEnabled,
            interactionSource = interactionSource,
            decorationBox = @Composable { innerTextField ->
                val isFocused = interactionSource.collectIsFocusedAsState().value

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .imePadding(), verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isFocused && text.isEmpty()) {
                        Text(
                            text = "대답하기",
                            style = WizdumTheme.typography.body1,
                            color = Black500
                        )
                    }
                    innerTextField()
                }
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .background(
                    color = if (isInputEnabled) Green200 else Black200,
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable {
                    sendMessage(text)
                    text = ""
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_btn_send_enable),
                contentDescription = "보내기"
            )
        }
    }
}