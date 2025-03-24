package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.AuthApi
import com.teamwizdum.wizdum.data.api.UserApi
import com.teamwizdum.wizdum.data.model.request.TokenRequest
import com.teamwizdum.wizdum.data.model.response.TokenResponse
import com.teamwizdum.wizdum.data.model.response.UserResponse
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val authApi: AuthApi,
    private val userApi: UserApi,
) {

    suspend fun signUp(accessToken: String): Result<TokenResponse> {
        return runCatching { authApi.signUp(TokenRequest(accessToken = accessToken)) }
    }

    suspend fun login(): Result<Response<Unit>> {
        return runCatching { authApi.login() }
    }

    suspend fun logout(): Result<Response<Unit>> {
        return runCatching { authApi.logout() }
    }

    suspend fun getUserInfo(): Result<UserResponse> {
        return runCatching { userApi.getUserInfo() }
    }

    suspend fun withdraw(): Result<Response<Unit>> {
        return runCatching { userApi.withdraw() }
    }
}