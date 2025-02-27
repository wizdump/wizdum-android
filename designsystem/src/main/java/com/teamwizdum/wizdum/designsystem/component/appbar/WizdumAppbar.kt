package com.teamwizdum.wizdum.designsystem.component.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwizdum.wizdum.designsystem.R
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme

@Composable
fun TitleAppbar(modifier: Modifier = Modifier, title: String = "") {
    BasicAppBar(
        modifier = modifier,
        title = {
            Text(text = title, style = WizdumTheme.typography.body1_semib)
        }
    )
}

@Composable
fun BackAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onNavigateBack: () -> Unit = {},
) {
    BasicAppBar(
        modifier = modifier,
        startIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_btn_arrow_back),
                contentDescription = "뒤로가기",
                modifier = modifier
                    .clickable {
                        onNavigateBack()
                    }
                    .padding(16.dp)
            )
        },
        title = {
            Text(text = title, style = WizdumTheme.typography.body1_semib)
        }
    )
}

@Composable
fun CloseAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onClose: () -> Unit = {},
) {
    BasicAppBar(
        modifier = modifier,
        title = {
            Text(text = title, style = WizdumTheme.typography.body1_semib)
        },
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.ic_btn_close),
                contentDescription = "취소",
                modifier = modifier
                    .clickable {
                        onClose()
                    }
                    .padding(16.dp)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BackAppBarPreview() {
    WizdumTheme {
        BackAppBar(title = "title")
    }
}

@Preview(showBackground = true)
@Composable
fun CloseAppBarPreview() {
    WizdumTheme {
        CloseAppBar()
    }
}