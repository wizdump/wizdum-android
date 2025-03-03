package com.teamwizdum.wizdum.data.api

import com.teamwizdum.wizdum.data.model.response.HomeResponse
import retrofit2.http.GET

interface HomeApi {
    @GET("lectures/main")
    suspend fun getHomeData(): HomeResponse
}