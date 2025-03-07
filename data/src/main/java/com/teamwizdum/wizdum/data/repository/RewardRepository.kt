package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.RewardApi
import com.teamwizdum.wizdum.data.model.response.RewardResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RewardRepository @Inject constructor(
    private val rewardApi: RewardApi
) {
    suspend fun postReward(lectureId: Int): Flow<Response<Unit>> = flow {
        emit(rewardApi.postReward(lectureId))
    }

    suspend fun getReward(lectureId: Int): Flow<RewardResponse> = flow {
        emit(rewardApi.getReward(lectureId, lectureId))
    }
}