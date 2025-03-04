package com.teamwizdum.wizdum.data.di

import com.teamwizdum.wizdum.data.api.AuthApi
import com.teamwizdum.wizdum.data.api.OnboardingApi
import com.teamwizdum.wizdum.data.api.QuestApi
import com.teamwizdum.wizdum.data.api.RewardApi
import com.teamwizdum.wizdum.data.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
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
    fun provideRewardApi(retrofit: Retrofit): RewardApi {
        return retrofit.create(RewardApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}