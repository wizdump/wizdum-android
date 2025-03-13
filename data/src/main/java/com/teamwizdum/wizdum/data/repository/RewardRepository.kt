package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.RewardApi
import com.teamwizdum.wizdum.data.model.response.RewardResponse
import retrofit2.Response
import javax.inject.Inject

class RewardRepository @Inject constructor(
    private val rewardApi: RewardApi
) {
    suspend fun postReward(lectureId: Int): Result<Response<Unit>> {
        return runCatching { rewardApi.postReward(lectureId) }
    }

    suspend fun getReward(lectureId: Int): Result<RewardResponse> {
        return runCatching { rewardApi.getReward(lectureId, lectureId) }
    }
}