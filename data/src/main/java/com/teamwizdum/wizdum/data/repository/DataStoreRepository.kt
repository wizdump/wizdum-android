package com.teamwizdum.wizdum.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.teamwizdum.wizdum.data.Constants
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
){
    suspend fun setOnboardingCompleted(isDone: Boolean) =
        dataStore.edit { preferences ->
            preferences[Constants.onboardingCompletedKey] = isDone
        }

    suspend fun isOnboardingCompleted(): Boolean? =
        dataStore.data.map { it[Constants.onboardingCompletedKey] }.firstOrNull()
}