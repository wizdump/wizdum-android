package com.teamwizdum.wizdum.data.repository

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject

class TokenRepository @Inject constructor(context: Application) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedPrefs = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getAccessToken(): String? {
        return encryptedPrefs.getString(ACCESS_TOKEN, "")
    }

    fun getRefreshToken(): String? {
        return encryptedPrefs.getString(REFRESH_TOKEN, "")
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        encryptedPrefs.edit().putString(ACCESS_TOKEN, accessToken).apply()
        encryptedPrefs.edit().putString(REFRESH_TOKEN, refreshToken).apply()
    }

    suspend fun deleteTokens() {
        encryptedPrefs.edit().clear().apply()
    }

    companion object {
        private const val ACCESS_TOKEN = "access-token"
        private const val REFRESH_TOKEN = "refresh-token"
    }
}