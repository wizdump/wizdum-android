package com.teamwizdum.wizdum.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.InterestResponse
import com.teamwizdum.wizdum.data.model.response.KeywordResponse
import com.teamwizdum.wizdum.data.model.response.LevelResponse
import com.teamwizdum.wizdum.data.model.response.MentorDetailResponse
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import com.teamwizdum.wizdum.data.repository.DataStoreRepository
import com.teamwizdum.wizdum.data.repository.LectureRepository
import com.teamwizdum.wizdum.data.repository.OnboardingRepository
import com.teamwizdum.wizdum.feature.common.base.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val onboardingRepository: OnboardingRepository,
    private val lectureRepository: LectureRepository,
) : ViewModel() {

    private val _hasSeenOnboarding = MutableStateFlow(false)
    val hasSeenOnboarding: StateFlow<Boolean> = _hasSeenOnboarding

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<OnboardingUiEvent>()
    val eventFlow: SharedFlow<OnboardingUiEvent> = _eventFlow.asSharedFlow()

    fun getInterest() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            onboardingRepository.getInterests()
                .onSuccess { interests ->
                    _uiState.update { it.copy(interests = interests) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            handleException = ErrorState.DisplayError(
                                throwable = error,
                                retry = ::getInterest
                            )
                        )
                    }
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getLevel() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            onboardingRepository.getLevel()
                .onSuccess { data ->
                    _uiState.update { it.copy(levels = data) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            handleException = ErrorState.DisplayError(
                                throwable = error,
                                retry = ::getLevel
                            )
                        )
                    }
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getKeyword() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            onboardingRepository.getKeywords()
                .onSuccess { data ->
                    _uiState.update { it.copy(keywords = data) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            handleException = ErrorState.DisplayError(
                                throwable = error,
                                retry = ::getKeyword
                            )
                        )
                    }
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getMentors(interestId: Int, levelId: Int, categoryId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            onboardingRepository.getMentors(
                interestId = interestId,
                levelId = levelId,
                categoryId = categoryId
            ).onSuccess { data ->
                _uiState.update { it.copy(mentors = data) }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        handleException = ErrorState.DisplayError(
                            throwable = error,
                            retry = { getMentors(interestId, levelId, categoryId) }
                        )
                    )
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getMentorDetail(classId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            onboardingRepository.getMentorDetail(classId)
                .onSuccess { data ->
                    _uiState.update { it.copy(mentorDetail = data) }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            handleException = ErrorState.DisplayError(
                                throwable = error,
                                retry = { getMentorDetail(classId) })
                        )
                    }
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun startLecture(classId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            lectureRepository.startLecture(classId)
                .onSuccess {
                    _eventFlow.emit(OnboardingUiEvent.NavigateToLecture)
                }
                .onFailure { throwable ->
                    _eventFlow.emit(
                        OnboardingUiEvent.ShowErrorDialog(
                            throwable = throwable,
                            retry = { startLecture(classId) }
                        )
                    )
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }


    fun hasSeenOnboarding() {
        viewModelScope.launch {
            _hasSeenOnboarding.value = dataStoreRepository.isOnboardingCompleted() ?: false
        }
    }
}

data class OnboardingUiState(
    val hsSeenOnboarding: Boolean = false,
    val isLoading: Boolean = false,
    val interests: List<InterestResponse> = emptyList(),
    val levels: List<LevelResponse> = emptyList(),
    val keywords: List<KeywordResponse> = emptyList(),
    val mentors: List<MentorsResponse> = emptyList(),
    val mentorDetail: MentorDetailResponse = MentorDetailResponse(),
    val handleException: ErrorState = ErrorState.None,
)

sealed interface OnboardingUiEvent {
    object NavigateToLecture : OnboardingUiEvent
    data class ShowErrorDialog(val throwable: Throwable, val retry: () -> Unit) : OnboardingUiEvent
}