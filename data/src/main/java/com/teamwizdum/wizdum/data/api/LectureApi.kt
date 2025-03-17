package com.teamwizdum.wizdum.data.api

import com.teamwizdum.wizdum.data.model.ChatMessage
import com.teamwizdum.wizdum.data.model.response.FinishLectureResponse
import com.teamwizdum.wizdum.data.model.response.LectureResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LectureApi {
    @POST("lectures")
    suspend fun startLecture(@Query("classId") classId: Int): Response<Unit>

    @GET("lectures")
    suspend fun getLectures(@Query("classId") classId: Int): LectureResponse

    @PATCH("lectures/{lectureId}")
    suspend fun finishLecture(@Path("lectureId") lectureId: Int): FinishLectureResponse

    @GET("lectures/{lectureId}/chats")
    suspend fun getChatList(@Path("lectureId") lectureId: Int): List<ChatMessage>
}