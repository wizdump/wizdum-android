package com.teamwizdum.wizdum.feature.quest.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.quest.info.QuestStatus

@Composable
fun StatusCircle(isInProgress: Boolean, status: String) {
    val statusEnum = QuestStatus.fromString(status)
    var resId = R.drawable.ic_quest_in_progress

    // 포커스된 강의가 아닐 때
    if (!isInProgress) {
        resId = when(statusEnum) {
            QuestStatus.WAIT -> R.drawable.ic_quest_wait
            QuestStatus.DONE -> R.drawable.ic_quest_done
            else -> R.drawable.ic_quest_in_progress
        }
    }

    Image(painter = painterResource(resId), contentDescription = "퀘스트 상태")
}