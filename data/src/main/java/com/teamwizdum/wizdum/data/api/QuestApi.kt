package com.teamwizdum.wizdum.data.api

import com.teamwizdum.wizdum.data.model.response.QuestResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestApi {
    @GET("lectures")
    suspend fun getQuests(@Query("mentoId") mentorId: Int): QuestResponse
}