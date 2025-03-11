package com.teamwizdum.wizdum.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.HomeResponse
import com.teamwizdum.wizdum.data.repository.HomeRepository
import com.teamwizdum.wizdum.feature.common.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
) : ViewModel() {

    private val _homeData = MutableStateFlow<UiState<HomeResponse>>(UiState.Loading)
    val homeData: StateFlow<UiState<HomeResponse>> = _homeData

    fun getHomeData() {
        viewModelScope.launch {
            repository.getHomeData()
                .onSuccess { data ->
                    _homeData.value = UiState.Success(data)
                }.onFailure { error ->
                    _homeData.value = UiState.Failed(error.message)
                }
        }
    }
}