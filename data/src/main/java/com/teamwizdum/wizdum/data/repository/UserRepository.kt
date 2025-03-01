package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.AuthApi
import com.teamwizdum.wizdum.data.model.request.TokenRequest
import com.teamwizdum.wizdum.data.model.response.TokenResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val authApi: AuthApi) {

    suspend fun login(accessToken: String): Flow<TokenResponse> = flow {
        emit(authApi.login(TokenRequest(accessToken = accessToken)))
    }
}