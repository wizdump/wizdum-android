package com.teamwizdum.wizdum

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import timber.log.Timber

class WizdumApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}