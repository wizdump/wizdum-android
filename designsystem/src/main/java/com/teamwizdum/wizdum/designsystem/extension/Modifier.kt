package com.teamwizdum.wizdum.designsystem.extension

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.noRippleClickable(
    onClick: () -> Unit,
): Modifier = this.then(
    Modifier.combinedClickable(
        onClick = { onClick() },
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    )
)