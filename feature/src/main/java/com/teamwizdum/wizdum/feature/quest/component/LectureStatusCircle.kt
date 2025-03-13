package com.teamwizdum.wizdum.feature.quest.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.common.enums.LectureStatus

@Composable
fun StatusCircle(isInProgress: Boolean, status: String) {
    var resId = R.drawable.ic_quest_in_progress

    if (!isInProgress) {
        resId = when(status) {
            LectureStatus.WAIT.name -> LectureStatus.WAIT.questIconRes
            LectureStatus.DONE.name -> LectureStatus.DONE.questIconRes
            else -> LectureStatus.IN_PROGRESS.questIconRes
        }
    }

    Image(painter = painterResource(resId), contentDescription = "퀘스트 상태")
}