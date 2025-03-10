package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.OnboardingApi
import com.teamwizdum.wizdum.data.model.response.InterestResponse
import com.teamwizdum.wizdum.data.model.response.KeywordResponse
import com.teamwizdum.wizdum.data.model.response.MentorDetailResponse
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import com.teamwizdum.wizdum.data.model.response.LevelResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnboardingRepository @Inject constructor(
    private val onboardingApi: OnboardingApi,
) {
    suspend fun getInterests(): Flow<List<InterestResponse>> = flow {
        emit(onboardingApi.getInterests())
    }

    suspend fun getLevel(): Flow<List<LevelResponse>> = flow {
        emit(onboardingApi.getLevel())
    }

    suspend fun getKeywords(): Flow<List<KeywordResponse>> = flow {
        emit(onboardingApi.getKeywords())
    }

    suspend fun getMentors(
        interestId: Int,
        levelId: Int,
        categoryId: Int,
    ): Flow<List<MentorsResponse>> = flow {
        emit(
            onboardingApi.getMentors(
                interestId = interestId,
                levelId = levelId,
                categoryId = categoryId
            )
        )
    }

    suspend fun getMentorDetail(classId: Int): Flow<MentorDetailResponse> = flow {
        emit(
            onboardingApi.getMentorDetail(pathClassId = classId, queryClassId = classId)
        )
    }
}