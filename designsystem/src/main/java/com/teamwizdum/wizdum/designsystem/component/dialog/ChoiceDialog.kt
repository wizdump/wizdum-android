package com.teamwizdum.wizdum.designsystem.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black50
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun ChoiceDialog(
    dialogState: Boolean,
    title: String,
    subTitle: String = "",
    imgResId: Int? = null,
    confirmTitle: String,
    dismissTitle: String,
    confirmTextColor: Color = Color.White,
    dismissTextColor: Color = Black600,
    confirmButtonColor: Color = Green200,
    dismissButtonColor: Color = Black200,
    onConfirmRequest: () -> Unit,
    onDismissRequest: () -> Unit = {},
) {
    if (dialogState)
        Dialog(onDismissRequest = onDismissRequest) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Black50, shape = RoundedCornerShape(20.dp))
                    .border(width = 1.dp, color = Black200, shape = RoundedCornerShape(20.dp))
                    .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (imgResId != null) {
                    Image(
                        painter = painterResource(id = imgResId),
                        contentDescription = "다이얼로그 이미지"
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                }
                Text(
                    text = title,
                    style = WizdumTheme.typography.body1_semib,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (subTitle.isNotEmpty()) {
                    Text(
                        text = subTitle,
                        style = WizdumTheme.typography.body3,
                        color = Black600,
                        textAlign = TextAlign.Center,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    WizdumFilledButton(
                        modifier = Modifier.weight(1f),
                        title = dismissTitle,
                        backgroundColor = dismissButtonColor,
                        textColor = dismissTextColor,
                        textStyle = WizdumTheme.typography.body1_semib
                    ) {
                        onDismissRequest()
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    WizdumFilledButton(
                        modifier = Modifier.weight(1f),
                        title = confirmTitle,
                        backgroundColor = confirmButtonColor,
                        textColor = confirmTextColor,
                        textStyle = WizdumTheme.typography.body1_semib
                    ) {
                        onConfirmRequest()
                    }
                }
            }
        }
}

@Preview
@Composable
fun ChoiceDialogPreview() {
    WizdumTheme {
        ChoiceDialog(
            dialogState = true,
            title = "Title",
            subTitle = "내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용",
            confirmTitle = "네",
            dismissTitle = "아니오",
            onConfirmRequest = {},
            onDismissRequest = {}
        )
    }
}