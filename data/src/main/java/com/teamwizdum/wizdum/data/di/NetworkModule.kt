package com.teamwizdum.wizdum.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.teamwizdum.wizdum.data.network.TokenInterceptor
import com.teamwizdum.wizdum.data.retrofit.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TAG = "Retrofit"
    private const val BASE_URL = "http://211.188.49.238:8080/"

    @Singleton
    @Provides
    fun provideLoggingInterceptor(
        json: Json,
    ): HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
        when {
            !message.isJsonObject() && !message.isJsonArray() ->
                Timber.tag(TAG).d("CONNECTION INFO -> $message")

            else -> kotlin.runCatching {
                json.encodeToString(Json.parseToJsonElement(message))
            }.onSuccess {
                Timber.tag(TAG).d(it)
            }.onFailure {
                Timber.tag(TAG).d(message)
            }
        }
    }.apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(tokenInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            prettyPrint = true
            coerceInputValues = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }
}

fun String.isJsonObject(): Boolean = runCatching { JSONObject(this) }.isSuccess

fun String.isJsonArray(): Boolean = runCatching { JSONArray(this) }.isSuccess