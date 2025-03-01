package com.teamwizdum.wizdum.data.api

import com.teamwizdum.wizdum.data.model.request.TokenRequest
import com.teamwizdum.wizdum.data.model.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth")
    suspend fun login(@Body accessToken: TokenRequest): TokenResponse
}