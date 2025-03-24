package com.teamwizdum.wizdum.feature.lecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.LectureResponse
import com.teamwizdum.wizdum.data.repository.LectureRepository
import com.teamwizdum.wizdum.data.repository.RewardRepository
import com.teamwizdum.wizdum.feature.common.base.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LectureViewModel @Inject constructor(
    private val lectureRepository: LectureRepository,
    private val rewardRepository: RewardRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LectureUiState())
    val uiState: StateFlow<LectureUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LectureUiEvent>()
    val eventFlow: SharedFlow<LectureUiEvent> = _eventFlow

    fun getLectures(classId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            lectureRepository.getLectures(classId)
                .onSuccess { data ->
                    _uiState.update { it.copy(lectureInfo = data) }
                }.onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            handleException = ErrorState.DisplayError(
                                throwable = throwable,
                                retry = { getLectures(classId) }
                            )
                        )
                    }
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun postReward(lectureId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            rewardRepository.postReward(lectureId)
                .onSuccess {
                    _eventFlow.emit(LectureUiEvent.NavigateToReward)
                }
                .onFailure { throwable ->
                    _eventFlow.emit(
                        LectureUiEvent.ShowErrorDialog(
                            throwable = throwable,
                            retry = { postReward(lectureId) }
                        )
                    )
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

data class LectureUiState(
    val isLoading: Boolean = false,
    val lectureInfo: LectureResponse = LectureResponse(),
    val handleException: ErrorState = ErrorState.None,
)

sealed interface LectureUiEvent {
    data object NavigateToReward : LectureUiEvent
    data class ShowErrorDialog(val throwable: Throwable, val retry: () -> Unit) : LectureUiEvent
}