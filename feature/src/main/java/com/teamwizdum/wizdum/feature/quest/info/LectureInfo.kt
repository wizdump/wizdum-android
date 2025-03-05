package com.teamwizdum.wizdum.feature.quest.info

import android.os.Parcelable


data class ChatRoomInfo(
    val lectureId: Int = 0,
    val orderSeq: Int = 0,
    val lectureTitle: String = "",
    val lectureStatus: String = "",
    val isLastLecture: Boolean = false,
    val mentorName: String = "",
    val mentorImgUrl: String = "",
    val userName: String = "",
)