package com.teamwizdum.wizdum.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.teamwizdum.wizdum.designsystem.R


val robotoFontFamily = FontFamily(
    Font(R.font.roboto_medium),
    Font(R.font.roboto_semibold),
    Font(R.font.roboto_bold)
)

private val robotoStyle = TextStyle(
    fontFamily = robotoFontFamily,
    letterSpacing = TextUnit(-0.02f, TextUnitType.Sp),
)

private val robotoBoldStyle = robotoStyle.copy(
    fontWeight = FontWeight.Bold
)

private val robotoSemiBoldStyle = robotoStyle.copy(
    fontWeight = FontWeight.SemiBold
)

private val robotoMediumStyle = robotoStyle.copy(
    fontWeight = FontWeight.Medium
)

data class WizdumTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3_semib: TextStyle,
    val h3: TextStyle,
    val body1_semib: TextStyle,
    val body1: TextStyle,
    val body2_semib: TextStyle,
    val body2: TextStyle,
    val body3_semib: TextStyle,
    val body3: TextStyle,
)


val Typograhy = WizdumTypography(
    h1 = robotoBoldStyle.copy(
        fontSize = 32.sp,
        lineHeight = 32.sp * 1.6f,
        color = Black700
    ),
    h2 = robotoSemiBoldStyle.copy(
        fontSize = 20.sp,
        lineHeight = 20.sp * 1.6f,
        color = Black700
    ),
    h3_semib = robotoSemiBoldStyle.copy(
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6f,
        color = Black700
    ),
    h3 = robotoMediumStyle.copy(
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6f,
        color = Black700
    ),
    body1_semib = robotoSemiBoldStyle.copy(
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6f,
        color = Black700
    ),
    body1 = robotoMediumStyle.copy(
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6f,
        color = Black700
    ),
    body2_semib = robotoSemiBoldStyle.copy(
        fontSize = 12.sp,
        lineHeight = 12.sp * 1.6f,
        color = Black700
    ),
    body2 = robotoMediumStyle.copy(
        fontSize = 12.sp,
        lineHeight = 12.sp * 1.6f,
        color = Black700
    ),
    body3_semib = robotoSemiBoldStyle.copy(
        fontSize = 10.sp,
        lineHeight = 10.sp * 1.6f,
        color = Black700
    ),
    body3 = robotoMediumStyle.copy(
        fontSize = 10.sp,
        lineHeight = 10.sp * 1.6f,
        color = Black700
    ),
)

val LocalTypography = staticCompositionLocalOf {
    WizdumTypography(
        h1 = robotoStyle,
        h2 = robotoStyle,
        h3_semib = robotoStyle,
        h3 = robotoStyle,
        body3_semib = robotoStyle,
        body3 = robotoStyle,
        body2_semib = robotoStyle,
        body2 = robotoStyle,
        body1_semib = robotoStyle,
        body1 = robotoStyle
    )
}