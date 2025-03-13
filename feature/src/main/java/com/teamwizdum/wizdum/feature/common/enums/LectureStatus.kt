package com.teamwizdum.wizdum.feature.common.enums

import androidx.compose.ui.graphics.Color
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.Green50
import com.teamwizdum.wizdum.designsystem.theme.Success
import com.teamwizdum.wizdum.feature.R

enum class LectureStatus(
    val text: String,
    val textColor: Color,
    val borderColor: Color,
    val backgroundColor: Color,
    val badgeIconRes: Int,
    val questIconRes: Int,
) {
    WAIT(
        text = "수강전",
        textColor = Black600,
        borderColor = Black200,
        backgroundColor = Black100,
        badgeIconRes = R.drawable.ic_badge_wait,
        questIconRes = R.drawable.ic_quest_wait
    ),
    IN_PROGRESS(
        text = "수강중",
        textColor = Green200,
        borderColor = Green200,
        backgroundColor = Green50,
        badgeIconRes = R.drawable.ic_badge_in_progress,
        questIconRes = R.drawable.ic_quest_in_progress
    ),
    DONE(
        text = "수강 완료",
        textColor = Success,
        borderColor = Success,
        backgroundColor = Black100,
        badgeIconRes = R.drawable.ic_badge_done,
        questIconRes = R.drawable.ic_quest_done
    );
}