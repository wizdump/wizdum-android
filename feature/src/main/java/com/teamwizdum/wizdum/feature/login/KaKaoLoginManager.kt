package com.teamwizdum.wizdum.feature.login

import android.content.Context
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import timber.log.Timber

object KaKaoLoginManager {

    fun login(
        context: Context,
        onSuccess: (String) -> Unit = {},
        onFailed: (Throwable?) -> Unit = {},
    ) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context = context)) {
            Timber.tag("KAKAO").d("카카오톡 로그인")
            loginWithKakaoTalk(
                context = context,
                onSuccess = onSuccess,
                onFailed = { error ->
                    // 의도적인 로그인 취소가 아닌 경우 카카오 계정으로 로그인 시도
                    if (error is AuthError || (error is ClientError && error.reason == ClientErrorCause.Cancelled)) {
                        loginWithKakaoAccount(
                            context = context,
                            onSuccess = onSuccess,
                            onFailed = onFailed
                        )
                    } else {
                        onFailed(error)
                    }
                })
        } else {
            Timber.tag("KAKAO").d("카카오 계정 로그인")
            loginWithKakaoAccount(context = context, onSuccess = onSuccess, onFailed = onFailed)
        }
    }

    private fun loginWithKakaoTalk(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (token != null) {
                Timber.tag("KAKO_TALK").d("로그인 성공 : ${token.accessToken}")
                onSuccess(token.accessToken)
            } else {
                Timber.tag("KAKO_TALK").d("로그인 실패 : ${error?.cause}")
                onFailed(error)
            }
        }
    }

    private fun loginWithKakaoAccount(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (token != null) {
                Timber.tag("KAKO_ACCOUNT").d("로그인 성공 : ${token.accessToken}")
                onSuccess(token.accessToken)
            } else {
                Timber.tag("KAKO_ACCOUNT").d("로그인 실패 : ${error?.cause}")
                onFailed(error)
            }
        }
    }
}