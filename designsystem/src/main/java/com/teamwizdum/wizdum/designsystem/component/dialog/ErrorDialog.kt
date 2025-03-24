package com.teamwizdum.wizdum.designsystem.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.teamwizdum.wizdum.designsystem.R
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black700
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun ErrorDialog(
    dialogState: Boolean,
    retry: () -> Unit,
) {
    ChoiceDialog(
        dialogState = dialogState,
        title = "인터넷 연결이 불안정해요",
        subTitle = "Wi-Fi나 데이터 연결 상태를 확인하고\n다시 시도해주세요.",
        imgResId = R.drawable.img_network,
        confirmTitle = "재시도",
        dismissTitle = "취소",
        confirmTextColor = Color.White,
        dismissTextColor = Black700,
        confirmButtonColor = Black700,
        dismissButtonColor = Black200,
        onConfirmRequest = retry
    )
}

@Preview
@Composable
fun ErrorDialogPreview() {
    WizdumTheme {
        ErrorDialog(dialogState = true) {}
    }
}