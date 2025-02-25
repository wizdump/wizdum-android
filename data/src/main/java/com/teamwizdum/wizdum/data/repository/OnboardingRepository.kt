package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.OnboardingApi
import com.teamwizdum.wizdum.data.model.KeywordResponse
import com.teamwizdum.wizdum.data.model.QuestionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnboardingRepository @Inject constructor(
    private val onboardingApi: OnboardingApi,
) {
    suspend fun getKeywords(): Flow<List<KeywordResponse>> = flow {
        emit(onboardingApi.getKeywords())
    }

    suspend fun getQuestions(keywordId: Int): Flow<List<QuestionResponse>> = flow {
        emit(onboardingApi.getQuestions(keywordId))
    }
}