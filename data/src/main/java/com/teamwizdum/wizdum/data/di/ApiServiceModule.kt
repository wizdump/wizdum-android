package com.teamwizdum.wizdum.data.di

import com.teamwizdum.wizdum.data.api.AuthApi
import com.teamwizdum.wizdum.data.api.HomeApi
import com.teamwizdum.wizdum.data.api.OnboardingApi
import com.teamwizdum.wizdum.data.api.QuestApi
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

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideQuestApi(retrofit: Retrofit): QuestApi {
        return retrofit.create(QuestApi::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeApi(retrofit: Retrofit): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }
}