package com.teamwizdum.wizdum.feature.common.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.teamwizdum.wizdum.designsystem.component.badge.TextWithIconBadge
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.common.enums.LectureStatus

@Composable
fun LectureStatusBadge(status: String, modifier: Modifier = Modifier) {
    var resId = R.drawable.ic_badge_in_progress
    var text = ""
    var textColor = Green200
    var backgroundColor = Green200
    var borderColor = Green200

    when(status) {
        LectureStatus.WAIT.name -> {
            resId = LectureStatus.WAIT.badgeIconRes
            text = LectureStatus.WAIT.text
            textColor = LectureStatus.WAIT.textColor
            backgroundColor = LectureStatus.WAIT.backgroundColor
            borderColor = LectureStatus.WAIT.borderColor
        }
        LectureStatus.IN_PROGRESS.name -> {
            resId = LectureStatus.IN_PROGRESS.badgeIconRes
            text = LectureStatus.IN_PROGRESS.text
            textColor = LectureStatus.IN_PROGRESS.textColor
            backgroundColor = LectureStatus.IN_PROGRESS.backgroundColor
            borderColor = LectureStatus.IN_PROGRESS.borderColor
        }
        LectureStatus.DONE.name -> {
            resId = LectureStatus.DONE.badgeIconRes
            text = LectureStatus.DONE.text
            textColor = LectureStatus.DONE.textColor
            backgroundColor = LectureStatus.DONE.backgroundColor
            borderColor = LectureStatus.DONE.borderColor
        }
    }

    TextWithIconBadge(
        title = text,
        resId = resId,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        textColor = textColor,
        modifier = modifier
    )
}