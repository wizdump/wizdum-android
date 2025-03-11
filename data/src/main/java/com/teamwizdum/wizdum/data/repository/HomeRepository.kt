package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.api.HomeApi
import com.teamwizdum.wizdum.data.model.response.HomeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(private val homeApi: HomeApi) {
    suspend fun getHomeData(): Result<HomeResponse> {
        return runCatching { homeApi.getHomeData() }
    }
}