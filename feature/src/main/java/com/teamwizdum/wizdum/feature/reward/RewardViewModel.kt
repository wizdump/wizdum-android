package com.teamwizdum.wizdum.feature.reward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.RewardResponse
import com.teamwizdum.wizdum.data.repository.RewardRepository
import com.teamwizdum.wizdum.feature.common.base.ErrorState
import com.teamwizdum.wizdum.feature.common.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
    private val rewardRepository: RewardRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RewardUiState())
    val uiState: StateFlow<RewardUiState> = _uiState

    fun getReward(lectureId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            rewardRepository.getReward(lectureId)
                .onSuccess { data ->
                    _uiState.update { it.copy(reward = data) }
                }.onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            handleException = ErrorState.DisplayError(
                                throwable = throwable,
                                retry = { getReward(lectureId) }
                            )
                        )
                    }
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

data class RewardUiState(
    val isLoading: Boolean = false,
    val reward: RewardResponse = RewardResponse(),
    val handleException: ErrorState = ErrorState.None,
)