package com.teamwizdum.wizdum.data.api

import com.teamwizdum.wizdum.data.model.request.MentorsRequest
import com.teamwizdum.wizdum.data.model.response.KeywordResponse
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import com.teamwizdum.wizdum.data.model.response.QuestionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OnboardingApi {
    @GET("questions/keywords")
    suspend fun getKeywords(): List<KeywordResponse>

    @GET("questions")
    suspend fun getQuestions(
        @Query("keywordId") keywordId: Int,
    ): List<QuestionResponse>

    @POST("/mentos")
    suspend fun postMentors(
        @Body mentorsRequest: MentorsRequest
    ): MentorsResponse
}