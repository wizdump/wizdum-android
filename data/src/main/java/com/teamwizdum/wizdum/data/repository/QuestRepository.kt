package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.QuestApi
import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.data.model.response.FinishQuestResponse
import com.teamwizdum.wizdum.data.model.response.LectureResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class QuestRepository @Inject constructor(
    private val questApi: QuestApi,
) {
    suspend fun startQuest(classId: Int): Result<Response<Unit>> {
        return runCatching { questApi.startQuest(classId) }
    }

    suspend fun getQuests(classId: Int): Result<LectureResponse> {
        return runCatching { questApi.getQuests(classId) }
    }

    suspend fun finishQuest(lectureId: Int): Result<FinishQuestResponse> {
        return runCatching { questApi.finishQuest(lectureId) }
    }

    suspend fun getChatList(lectureId: Int): Flow<List<ChatMessage>> = flow {
        emit(questApi.getChatList(lectureId))
    }
}