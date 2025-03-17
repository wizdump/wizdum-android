package com.teamwizdum.wizdum.data

import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
    private const val ONBOARDING_COMPLETED = "onboarding_completed"
    val onboardingCompletedKey = booleanPreferencesKey(ONBOARDING_COMPLETED)
}