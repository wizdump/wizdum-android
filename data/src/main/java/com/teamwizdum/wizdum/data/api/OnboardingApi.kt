package com.teamwizdum.wizdum.data.api

import com.teamwizdum.wizdum.data.model.response.KeywordResponse
import com.teamwizdum.wizdum.data.model.response.MentorDetailResponse
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import com.teamwizdum.wizdum.data.model.response.QuestionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OnboardingApi {
    @GET("questions/keywords")
    suspend fun getKeywords(): List<KeywordResponse>

    @GET("questions")
    suspend fun getQuestions(
        @Query("keywordId") keywordId: Int,
    ): List<QuestionResponse>

    @GET("mentos")
    suspend fun getMentors(
        @Query("categoryId") categoryId: Int,
        @Query("useAiMento") useAiMentor: Boolean
    ): List<MentorsResponse>

    @GET("mentos/{classId}")
    suspend fun getMentorDetail(
        @Path("classId") pathClassId: Int,
        @Query("classId") queryClassId: Int
    ): MentorDetailResponse
}