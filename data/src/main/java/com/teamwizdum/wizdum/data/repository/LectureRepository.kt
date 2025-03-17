package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.LectureApi
import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.data.model.response.FinishLectureResponse
import com.teamwizdum.wizdum.data.model.response.LectureResponse
import retrofit2.Response
import javax.inject.Inject

class LectureRepository @Inject constructor(
    private val lectureApi: LectureApi,
) {
    suspend fun startLecture(classId: Int): Result<Response<Unit>> {
        return runCatching { lectureApi.startLecture(classId) }
    }

    suspend fun getLectures(classId: Int): Result<LectureResponse> {
        return runCatching { lectureApi.getLectures(classId) }
    }

    suspend fun finishLecture(lectureId: Int): Result<FinishLectureResponse> {
        return runCatching { lectureApi.finishLecture(lectureId) }
    }

    suspend fun getChatList(lectureId: Int): Result<List<ChatMessage>> {
        return runCatching { lectureApi.getChatList(lectureId) }
    }
}