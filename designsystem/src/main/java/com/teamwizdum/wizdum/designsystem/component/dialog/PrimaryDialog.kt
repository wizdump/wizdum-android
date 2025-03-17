package com.teamwizdum.wizdum.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black50
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun PrimaryDialog(
    dialogState: Boolean,
    title: String,
    subTitle: String = "",
    dismissTitle: String,
    onDismissRequest: () -> Unit,
) {
    if (dialogState)
        Dialog(onDismissRequest = onDismissRequest) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Black50, shape = RoundedCornerShape(20.dp))
                    .border(width = 1.dp, color = Black200, shape = RoundedCornerShape(20.dp))
                    .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = WizdumTheme.typography.h3,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subTitle,
                    style = WizdumTheme.typography.body2,
                    color = Black600,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    WizdumFilledButton(
                        modifier = Modifier.weight(1f),
                        title = dismissTitle,
                        textStyle = WizdumTheme.typography.body1_semib
                    ) {
                        onDismissRequest()
                    }
                }
            }
        }
}

@Preview
@Composable
fun PrimaryDialogPreview() {
    WizdumTheme {
        PrimaryDialog(
            dialogState = true,
            title = "Title",
            subTitle = "내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용 내용",
            dismissTitle = "네",
            onDismissRequest = {}
        )
    }
}