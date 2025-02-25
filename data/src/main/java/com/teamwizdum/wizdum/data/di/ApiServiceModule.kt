package com.teamwizdum.wizdum.data.di

import com.teamwizdum.wizdum.data.api.OnboardingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun provideOnboardingApi(retrofit: Retrofit): OnboardingApi {
        return retrofit.create(OnboardingApi::class.java)
    }
}