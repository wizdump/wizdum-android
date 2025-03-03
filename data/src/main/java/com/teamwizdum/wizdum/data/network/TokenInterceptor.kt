package com.teamwizdum.wizdum.data.network

import com.teamwizdum.wizdum.data.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenRepository.getAccessToken()
        }

        val request = chain.request().newBuilder().apply {
            addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(request.build())
    }
}