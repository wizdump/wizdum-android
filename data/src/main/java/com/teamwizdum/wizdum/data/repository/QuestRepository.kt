package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.QuestApi
import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.data.model.response.FinishQuestResponse
import com.teamwizdum.wizdum.data.model.response.QuestResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class QuestRepository @Inject constructor(
    private val questApi: QuestApi,
) {
    suspend fun startQuest(mentorId: Int): Flow<Response<Unit>> = flow {
        emit(questApi.startQuest(mentorId = mentorId))
    }

    suspend fun getQuests(mentorId: Int): Flow<QuestResponse> = flow {
        emit(questApi.getQuests(mentorId))
    }

    suspend fun finishQuest(lectureId: Int): Flow<FinishQuestResponse> = flow {
        emit(questApi.finishQuest(lectureId))
    }

    suspend fun getChatList(lectureId: Int): Flow<List<ChatMessage>> = flow {
        emit(questApi.getChatList(lectureId))
    }
}