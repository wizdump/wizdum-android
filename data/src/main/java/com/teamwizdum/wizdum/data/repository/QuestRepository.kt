package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.QuestApi
import com.teamwizdum.wizdum.data.model.response.QuestResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuestRepository @Inject constructor(
    private val questApi: QuestApi,
) {
    suspend fun getQuests(mentorId: Int): Flow<QuestResponse> = flow {
        emit(questApi.getQuests(mentorId))
    }
}