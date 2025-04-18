package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.AuthApi
import com.teamwizdum.wizdum.data.api.UserApi
import com.teamwizdum.wizdum.data.model.request.TokenRequest
import com.teamwizdum.wizdum.data.model.response.TokenResponse
import com.teamwizdum.wizdum.data.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    suspend fun logout(): Flow<Response<Unit>> = flow {
        emit(authApi.logout())
    }

    suspend fun getUserInfo(): Flow<UserResponse> = flow {
        emit(userApi.getUserInfo())
    }

    suspend fun withdraw(): Flow<Response<Unit>> = flow {
        emit(userApi.withdraw())
    }
}