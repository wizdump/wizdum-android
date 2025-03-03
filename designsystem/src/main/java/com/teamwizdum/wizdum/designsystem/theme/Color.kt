package com.teamwizdum.wizdum.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Primary Color
val Green100 = Color(0xFFE8F5E9)
val Green50 = Color(0xFFFBFEFC)
val Green200 = Color(0xFF00C853)

// Secondary Color
val Black50 = Color(0xFFFFFFFF)
val Black100 = Color(0xFFFAFAFA)
val Black200 = Color(0xFFF0F0F0)
val Black300 = Color(0xFFDDDDDD)
val Black400 = Color(0xFFBBBBBB)
val Black500 = Color(0xFFAAAAAA)
val Black600 = Color(0xFF666666)
val Black700 = Color(0xFF333333)

// Status
val Info = Color(0xFFD4E157)
val Warning = Color(0xFFFFB112)
val Error = Color(0xFFDE3412)
val Success = Color(0xFF8C9EFF)

val LightColorScheme = WizdumColorScheme(
    background = Black100,
    primary = Green200,
    secondary = Black700,
    warning = Warning,
    error = Error
)

data class WizdumColorScheme(
    val background: Color,
    val primary: Color,
    val secondary: Color,
    val warning: Color,
    val error: Color
)

val LocalColorScheme = staticCompositionLocalOf {
    WizdumColorScheme(
        background = Color.Unspecified,
        primary = Color.Unspecified,
        secondary = Color.Unspecified,
        warning = Color.Unspecified,
        error = Color.Unspecified,
    )
}

