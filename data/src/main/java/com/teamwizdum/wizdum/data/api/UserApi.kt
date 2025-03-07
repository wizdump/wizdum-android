package com.teamwizdum.wizdum.data.api

import com.teamwizdum.wizdum.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET

interface UserApi {
    @GET("user")
    suspend fun getUserInfo(): UserResponse

    @DELETE("user")
    suspend fun withdraw(): Response<Unit>
}