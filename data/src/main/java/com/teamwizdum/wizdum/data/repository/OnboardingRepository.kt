package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.OnboardingApi
import com.teamwizdum.wizdum.data.model.response.InterestResponse
import com.teamwizdum.wizdum.data.model.response.KeywordResponse
import com.teamwizdum.wizdum.data.model.response.LevelResponse
import com.teamwizdum.wizdum.data.model.response.MentorDetailResponse
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import javax.inject.Inject

class OnboardingRepository @Inject constructor(
    private val onboardingApi: OnboardingApi,
) {
    suspend fun getInterests(): Result<List<InterestResponse>> {
        return runCatching { onboardingApi.getInterests() }
    }


    suspend fun getLevel(): Result<List<LevelResponse>> {
        return runCatching { onboardingApi.getLevel() }
    }


    suspend fun getKeywords(): Result<List<KeywordResponse>> {
        return runCatching { onboardingApi.getKeywords() }
    }


    suspend fun getMentors(
        interestId: Int,
        levelId: Int,
        categoryId: Int,
    ): Result<List<MentorsResponse>> {
        return runCatching {
            onboardingApi.getMentors(
                interestId = interestId,
                levelId = levelId,
                categoryId = categoryId
            )
        }
    }

    suspend fun getMentorDetail(classId: Int): Result<MentorDetailResponse> {
        return runCatching {
            onboardingApi.getMentorDetail(
                pathClassId = classId,
                queryClassId = classId
            )
        }
    }
}