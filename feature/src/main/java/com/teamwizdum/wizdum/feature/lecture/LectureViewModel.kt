package com.teamwizdum.wizdum.feature.lecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.LectureResponse
import com.teamwizdum.wizdum.data.repository.LectureRepository
import com.teamwizdum.wizdum.data.repository.RewardRepository
import com.teamwizdum.wizdum.feature.common.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LectureViewModel @Inject constructor(
    private val lectureRepository: LectureRepository,
    private val rewardRepository: RewardRepository
) : ViewModel() {

    private val _lectureInfo = MutableStateFlow<UiState<LectureResponse>>(UiState.Loading)
    val lectureInfo: StateFlow<UiState<LectureResponse>> = _lectureInfo

    fun getLectures(classId: Int) {
        viewModelScope.launch {
            lectureRepository.getLectures(classId)
                .onSuccess { data ->
                    _lectureInfo.value = UiState.Success(data)
                }.onFailure { error ->
                    _lectureInfo.value = UiState.Failed(error.message)
                }
        }
    }

    fun postReward(lectureId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            rewardRepository.postReward(lectureId).onSuccess {
                onSuccess()
            }
        }
    }
}