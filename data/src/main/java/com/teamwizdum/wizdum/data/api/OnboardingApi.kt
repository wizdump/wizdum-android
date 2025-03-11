package com.teamwizdum.wizdum.data.api

import com.teamwizdum.wizdum.data.model.response.InterestResponse
import com.teamwizdum.wizdum.data.model.response.KeywordResponse
import com.teamwizdum.wizdum.data.model.response.LevelResponse
import com.teamwizdum.wizdum.data.model.response.MentorDetailResponse
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OnboardingApi {
    @GET("questions/interest")
    suspend fun getInterests(): List<InterestResponse>

    @GET("questions/level")
    suspend fun getLevel(): List<LevelResponse>

    @GET("questions/keywords")
    suspend fun getKeywords(): List<KeywordResponse>

    @GET("mentos")
    suspend fun getMentors(
        @Query("interestId") interestId: Int,
        @Query("levelId") levelId: Int,
        @Query("categoryId") categoryId: Int,
    ): List<MentorsResponse>

    @GET("mentos/{classId}")
    suspend fun getMentorDetail(
        @Path("classId") pathClassId: Int,
        @Query("classId") queryClassId: Int
    ): MentorDetailResponse
}