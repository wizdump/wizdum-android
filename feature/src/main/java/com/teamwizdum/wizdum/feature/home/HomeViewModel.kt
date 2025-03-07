package com.teamwizdum.wizdum.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.HomeResponse
import com.teamwizdum.wizdum.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
) : ViewModel() {

    private val _homeData =
        MutableStateFlow<HomeResponse>(HomeResponse(username = "", myWizCount = 0, friendWithLectureCount = 0))
    val homeData: StateFlow<HomeResponse> = _homeData

    fun getHomeData() {
        viewModelScope.launch {
            repository.getHomeData().collect {
                _homeData.value = it
            }
        }
    }
}