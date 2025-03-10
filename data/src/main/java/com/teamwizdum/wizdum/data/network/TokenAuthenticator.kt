package com.teamwizdum.wizdum.data.network

import com.teamwizdum.wizdum.data.api.AuthApi
import com.teamwizdum.wizdum.data.model.request.RefreshTokenRequest
import com.teamwizdum.wizdum.data.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val authApi: AuthApi,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // 401 응답이 여러 번 발생할 경우 무한 루프 방지
        if (responseCount(response) >= MAX_RETRY_COUNT) return null

        return runBlocking {
            val refreshToken =  tokenRepository.getRefreshToken()

            // Refresh Token이 없으면 로그인 필요
            if (refreshToken.isNullOrBlank()) {
                tokenRepository.deleteTokens()
                return@runBlocking null
            }

            try {
                val tokenResponse = authApi.refreshAccessToken(RefreshTokenRequest(refreshToken))

                if (!tokenResponse.isSuccessful || tokenResponse.body() == null) {
                    tokenRepository.deleteTokens()
                    return@runBlocking null
                }

                val newAccessToken = tokenResponse.body()!!.accessToken
                val newRefreshToken = tokenResponse.body()!!.refreshToken

                tokenRepository.saveTokens(newAccessToken, newRefreshToken)

                response.request.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()
            } catch (e: Exception) {
                tokenRepository.deleteTokens()
                null
            }
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var priorResponse = response.priorResponse
        while (priorResponse != null) {
            count++
            priorResponse = priorResponse.priorResponse
        }
        return count
    }

    companion object {
        private const val MAX_RETRY_COUNT = 3
    }
}
