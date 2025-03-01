package com.teamwizdum.wizdum.feature.quest.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.teamwizdum.wizdum.designsystem.component.badge.TextWithIconBadge
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.quest.info.QuestStatus

@Composable
fun QuestStatusBadge(status: String, modifier: Modifier = Modifier) {
    val statusEnum = QuestStatus.fromString(status)

    var resId = R.drawable.ic_badge_in_progress
    var text = ""
    var textColor = Green200
    var backgroundColor = Green200
    var borderColor = Green200

    when(statusEnum) {
        QuestStatus.WAIT -> {
            resId = QuestStatus.WAIT.badgeIconRes
            text = QuestStatus.WAIT.text
            textColor = QuestStatus.WAIT.textColor
            backgroundColor = QuestStatus.WAIT.backgroundColor
            borderColor = QuestStatus.WAIT.borderColor
        }
        QuestStatus.IN_PROGRESS -> {
            resId = QuestStatus.IN_PROGRESS.badgeIconRes
            text = QuestStatus.IN_PROGRESS.text
            textColor = QuestStatus.IN_PROGRESS.textColor
            backgroundColor = QuestStatus.IN_PROGRESS.backgroundColor
            borderColor = QuestStatus.IN_PROGRESS.borderColor
        }
        QuestStatus.DONE -> {
            resId = QuestStatus.DONE.badgeIconRes
            text = QuestStatus.DONE.text
            textColor = QuestStatus.DONE.textColor
            backgroundColor = QuestStatus.DONE.backgroundColor
            borderColor = QuestStatus.DONE.borderColor
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