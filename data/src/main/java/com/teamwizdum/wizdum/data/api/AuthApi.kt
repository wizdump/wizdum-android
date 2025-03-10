package com.teamwizdum.wizdum.data.api

import com.teamwizdum.wizdum.data.model.request.RefreshTokenRequest
import com.teamwizdum.wizdum.data.model.request.TokenRequest
import com.teamwizdum.wizdum.data.model.response.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("auth")
    suspend fun signUp(@Body accessToken: TokenRequest): TokenResponse

    @GET("auth/token")
    suspend fun login(): Response<Unit>

    @DELETE("auth")
    suspend fun logout(): Response<Unit>

    @POST("auth/token")
    suspend fun refreshAccessToken(@Body refreshToken: RefreshTokenRequest): Response<TokenResponse>
}