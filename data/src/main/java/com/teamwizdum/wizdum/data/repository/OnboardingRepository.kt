package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.OnboardingApi
import com.teamwizdum.wizdum.data.model.request.MentorsRequest
import com.teamwizdum.wizdum.data.model.response.KeywordResponse
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import com.teamwizdum.wizdum.data.model.response.QuestionResponse
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

    suspend fun postMentors(questionId: Int, useAi: Boolean): Flow<MentorsResponse> = flow {
        emit(
            onboardingApi.postMentors(
                mentorsRequest = MentorsRequest(
                    questionId = questionId,
                    aiRecommendation = useAi

                )
            )
        )
    }
}