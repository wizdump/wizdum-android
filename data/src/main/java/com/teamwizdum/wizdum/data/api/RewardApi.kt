package com.teamwizdum.wizdum.data.api

import com.teamwizdum.wizdum.data.model.response.RewardResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RewardApi {
    @POST("reward")
    suspend fun postReward(@Query("classId") lectureId: Int): Response<Unit>

    @GET("reward/{classId}")
    suspend fun getReward(
        @Path("classId") pathLectureId: Int,
        @Query("classId") queryLectureId: Int
    ): RewardResponse
}