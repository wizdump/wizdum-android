package com.teamwizdum.wizdum.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.InterestResponse
import com.teamwizdum.wizdum.data.model.response.KeywordResponse
import com.teamwizdum.wizdum.data.model.response.LevelResponse
import com.teamwizdum.wizdum.data.model.response.MentorDetailResponse
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import com.teamwizdum.wizdum.data.repository.DataStoreRepository
import com.teamwizdum.wizdum.data.repository.OnboardingRepository
import com.teamwizdum.wizdum.data.repository.QuestRepository
import com.teamwizdum.wizdum.feature.common.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val onboardingRepository: OnboardingRepository,
    private val questRepository: QuestRepository,
) : ViewModel() {

    private val _hasSeenOnboarding = MutableStateFlow(false)
    val hasSeenOnboarding: StateFlow<Boolean> = _hasSeenOnboarding

    private val _interests = MutableStateFlow<UiState<List<InterestResponse>>>(UiState.Loading)
    val interests: StateFlow<UiState<List<InterestResponse>>> = _interests

    private val _levels = MutableStateFlow<UiState<List<LevelResponse>>>(UiState.Loading)
    val levels: StateFlow<UiState<List<LevelResponse>>> = _levels

    private val _keywords = MutableStateFlow<UiState<List<KeywordResponse>>>(UiState.Loading)
    val keywords: StateFlow<UiState<List<KeywordResponse>>> = _keywords

    private val _mentors = MutableStateFlow<UiState<List<MentorsResponse>>>(UiState.Loading)
    val mentors: StateFlow<UiState<List<MentorsResponse>>> = _mentors

    private val _mentorInfo = MutableStateFlow<UiState<MentorDetailResponse>>(UiState.Loading)
    val mentorInfo: StateFlow<UiState<MentorDetailResponse>> = _mentorInfo

    fun getInterest() {
        viewModelScope.launch {
            onboardingRepository.getInterests()
                .onSuccess { data ->
                    _interests.value = UiState.Success(data)
                }
                .onFailure { error ->
                    _interests.value = UiState.Failed(error.message)
                }
        }
    }

    fun getLevel() {
        viewModelScope.launch {
            onboardingRepository.getLevel()
                .onSuccess { data ->
                    _levels.value = UiState.Success(data)
                }
                .onFailure { error ->
                    _levels.value = UiState.Failed(error.message)
                }
        }
    }

    fun getKeyword() {
        viewModelScope.launch {
            onboardingRepository.getKeywords()
                .onSuccess { data ->
                    _keywords.value = UiState.Success(data)
                }
                .onFailure { error ->
                    _keywords.value = UiState.Failed(error.message)
                }
        }
    }

    fun getMentors(interestId: Int, levelId: Int, categoryId: Int) {
        viewModelScope.launch {
            onboardingRepository.getMentors(
                interestId = interestId,
                levelId = levelId,
                categoryId = categoryId
            ).onSuccess { data ->
                _mentors.value = UiState.Success(data)
            }.onFailure { error ->
                _mentors.value = UiState.Failed(error.message)
            }
        }
    }

    fun getMentorDetail(classId: Int) {
        viewModelScope.launch {
            onboardingRepository.getMentorDetail(classId)
                .onSuccess { data ->
                    _mentorInfo.value = UiState.Success(data)
                }.onFailure { error ->
                    _mentorInfo.value = UiState.Failed(error.message)
                }
        }
    }

    fun startQuest(classId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            questRepository.startQuest(classId)
                .onSuccess {
                    onSuccess()
                }
                .onFailure {
                    // 전역 에러 핸들링 처리
                }
        }
    }


    fun hasSeenOnboarding() {
        viewModelScope.launch {
            _hasSeenOnboarding.value = dataStoreRepository.isOnboardingCompleted() ?: false
        }
    }
}