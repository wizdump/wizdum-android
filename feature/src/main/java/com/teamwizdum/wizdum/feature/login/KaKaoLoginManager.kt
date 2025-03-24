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
        onSuccess: (String, String) -> Unit = {_, _ ->},
        onFailed: (Throwable?) -> Unit = {},
    ) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context = context)) {
            Timber.tag("KAKAO").d("카카오톡 로그인")
            loginWithKakaoTalk(
                context = context,
                onSuccess = onSuccess,
                onFailed = { error ->
                    // 의도적인 로그인 취소가 아닌 경우 카카오 계정으로 로그인 시도
                    if (error is AuthError || (error is ClientError && error.reason != ClientErrorCause.Cancelled)) {
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
        onSuccess: (String, String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (token != null) {
                Timber.tag("KAKAO_TALK").d("로그인 성공 : ${token.accessToken}")
                getKakaoUserInfo { nickName ->
                    onSuccess(token.accessToken, nickName)
                }
            } else {
                Timber.tag("KAKAO_TALK").d("로그인 실패 : ${error?.cause}")
                onFailed(error)
            }
        }
    }

    private fun loginWithKakaoAccount(
        context: Context,
        onSuccess: (String, String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (token != null) {
                Timber.tag("KAKAO_ACCOUNT").d("로그인 성공 : ${token.accessToken}")
                getKakaoUserInfo { nickName ->
                    onSuccess(token.accessToken, nickName)
                }
            } else {
                Timber.tag("KAKAO_ACCOUNT").d("로그인 실패 : ${error?.cause}")
                onFailed(error)
            }
        }
    }

    fun unLink() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Timber.tag("KAKAO_UNLINK").d("연결 끊기 실패 : ${error.cause}")
            } else {
                Timber.tag("KAKAO_UNLINK").d("연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    private fun getKakaoUserInfo(onSuccess: (String) -> Unit) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Timber.tag("KAKAO_USER").d("사용자 정보 요청 실패 : ${error.cause}")
            } else if (user != null) {
                onSuccess(user.kakaoAccount?.profile?.nickname ?: "")
                Timber.d(
                    "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"
                )
            }
        }
    }
}