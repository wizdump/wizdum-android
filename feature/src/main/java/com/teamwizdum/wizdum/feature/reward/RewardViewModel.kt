package com.teamwizdum.wizdum.feature.reward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.RewardResponse
import com.teamwizdum.wizdum.data.repository.RewardRepository
import com.teamwizdum.wizdum.feature.common.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
    private val rewardRepository: RewardRepository,
) : ViewModel() {

    private val _rewardInfo = MutableStateFlow<UiState<RewardResponse>>(UiState.Loading)
    val rewardInfo: StateFlow<UiState<RewardResponse>> = _rewardInfo

    fun getReward(lectureId: Int) {
        viewModelScope.launch {
            rewardRepository.getReward(lectureId)
                .onSuccess { data ->
                    _rewardInfo.value = UiState.Success(data)
                }.onFailure { error ->
                    _rewardInfo.value = UiState.Failed(error.message)
                }
        }
    }
}