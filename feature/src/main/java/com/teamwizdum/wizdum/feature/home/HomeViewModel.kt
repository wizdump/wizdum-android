package com.teamwizdum.wizdum.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.HomeResponse
import com.teamwizdum.wizdum.data.repository.HomeRepository
import com.teamwizdum.wizdum.feature.common.base.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun getHomeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.getHomeData()
                .onSuccess { data ->
                    _uiState.update { it.copy(homeInfo = data) }
                }.onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            handleException = ErrorState.DisplayError(
                                throwable = throwable,
                                retry = ::getHomeData
                            )
                        )
                    }
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val homeInfo: HomeResponse = HomeResponse(),
    val handleException: ErrorState = ErrorState.None,
)