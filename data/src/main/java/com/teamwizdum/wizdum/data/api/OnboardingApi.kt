package com.teamwizdum.wizdum.data.api

import com.teamwizdum.wizdum.data.model.KeywordResponse
import com.teamwizdum.wizdum.data.model.QuestionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OnboardingApi {
    @GET("questions/keywords")
    suspend fun getKeywords(): List<KeywordResponse>

    @GET("questions")
    suspend fun getQuestions(
        @Query("keywordId") keywordId: Int,
    ): List<QuestionResponse>
}