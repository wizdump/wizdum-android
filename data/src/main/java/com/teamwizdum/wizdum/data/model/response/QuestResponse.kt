package com.teamwizdum.wizdum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class QuestResponse(
    val mentoId: Int = 1,
    val mentoName: String = "",
    val userName: String = "",
    val filePath: String? = "", // TODO: 배경, 프로필사진 따로 받아야 함
    val mentoLectureTitle: String = "",
    val level: String = "",
    val lectures: List<LectureDetail> = emptyList(),
    val isFinished: Boolean = false, // 전체 강의 완료 여부
    val canGetWiz: Boolean = false,
) {
    fun getProgress(): Float {
        var totalWeight = 0f
        var totalLectures = 0

        lectures.forEach { lecture ->
            totalLectures++
            totalWeight += when (lecture.lectureStatus) {
                "IN_PROGRESS" -> 0.5f
                "DONE" -> 1f
                else -> 0f
            }
        }

        return if (totalLectures > 0) totalWeight / totalLectures else 0f
    }

    fun getFinishedLectureCount(): Int {
        return lectures.count { it.isFinished }
    }

    fun isLastLecture(orderSeq: Int): Boolean {
        return orderSeq == lectures.size
    }
}

@Serializable
data class LectureDetail(
    val lectureId: Int,
    val orderSeq: Int,
    val title: String,
    val preview: String,
    val lectureStatus: String,
    val isInProgress: Boolean, // 현재 진행 중인 강의
    val isFinished: Boolean,
)