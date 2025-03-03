package com.teamwizdum.wizdum.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun WizdumTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColorScheme provides LightColorScheme,
        LocalTypography provides Typograhy
    ) {
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = Color.Transparent.toArgb()
            }
        }

        MaterialTheme(
            colorScheme = lightColorScheme(background = WizdumTheme.colorScheme.background),
            content = content
        )
    }
}

object WizdumTheme {
    val colorScheme: WizdumColorScheme
        @Composable
        get() = LocalColorScheme.current
    val typography: WizdumTypography
        @Composable
        get() = LocalTypography.current
}