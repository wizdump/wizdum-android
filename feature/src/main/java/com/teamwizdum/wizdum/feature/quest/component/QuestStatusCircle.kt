package com.teamwizdum.wizdum.feature.quest.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.quest.info.QuestStatus

@Composable
fun StatusCircle(isInProgress: Boolean, status: String) {
    var resId = R.drawable.ic_quest_in_progress

    if (!isInProgress) {
        resId = when(status) {
            QuestStatus.WAIT.name -> QuestStatus.WAIT.questIconRes
            QuestStatus.DONE.name -> QuestStatus.DONE.questIconRes
            else -> QuestStatus.IN_PROGRESS.questIconRes
        }
    }

    Image(painter = painterResource(resId), contentDescription = "퀘스트 상태")
}