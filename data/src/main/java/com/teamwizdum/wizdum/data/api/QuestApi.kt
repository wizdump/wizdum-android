package com.teamwizdum.wizdum.data.api

import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.data.model.response.FinishQuestResponse
import com.teamwizdum.wizdum.data.model.response.QuestResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface QuestApi {
    @POST("lectures")
    suspend fun startQuest(@Query("mentoId") mentorId: Int): Response<Unit>

    @GET("lectures")
    suspend fun getQuests(@Query("mentoId") mentorId: Int): QuestResponse

    @PATCH("lectures/{lectureId}")
    suspend fun finishQuest(@Path("lectureId") lectureId: Int): FinishQuestResponse

    @GET("lectures/{lectureId}/chats")
    suspend fun getChatList(@Path("lectureId") lectureId: Int): List<ChatMessage>
}