package com.teamwizdum.wizdum.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.InterestResponse
import com.teamwizdum.wizdum.data.model.response.KeywordResponse
import com.teamwizdum.wizdum.data.model.response.MentorDetailResponse
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import com.teamwizdum.wizdum.data.model.response.LevelResponse
import com.teamwizdum.wizdum.data.repository.OnboardingRepository
import com.teamwizdum.wizdum.data.repository.QuestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val questRepository: QuestRepository,
) : ViewModel() {

    private val checkOnboarding = false

    private val _interests = MutableStateFlow<List<InterestResponse>>(emptyList())
    val interests: StateFlow<List<InterestResponse>> = _interests

    private val _level = MutableStateFlow<List<LevelResponse>>(emptyList())
    val level: StateFlow<List<LevelResponse>> = _level

    private val _keywords = MutableStateFlow<List<KeywordResponse>>(emptyList())
    val keywords: StateFlow<List<KeywordResponse>> = _keywords

    private val _mentors = MutableStateFlow<List<MentorsResponse>>(emptyList())
    val mentors: StateFlow<List<MentorsResponse>> = _mentors

    private val _mentorInfo = MutableStateFlow<MentorDetailResponse>(
        MentorDetailResponse(
            mentoName = "",
            classTitle = "",
            itemLevel = "LOW",
            friendWithLectureCount = 0
        )
    )
    val mentorInfo: StateFlow<MentorDetailResponse> = _mentorInfo

    fun getInterest() {
        viewModelScope.launch {
            onboardingRepository.getInterests().collect {
                _interests.value = it
            }
        }
    }

    fun getLevel() {
        viewModelScope.launch {
            onboardingRepository.getLevel().collect {
                _level.value = it
            }
        }
    }

    fun getKeyword(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            onboardingRepository.getKeywords().collect {
                _keywords.value = it
                onSuccess()
            }
        }
    }

    fun getMentors(interestId: Int, levelId: Int, categoryId: Int) {
        viewModelScope.launch {
            onboardingRepository.getMentors(
                interestId = interestId,
                levelId = levelId,
                categoryId = categoryId
            ).collect {
                _mentors.value = it
            }
        }
    }

    fun getMentorDetail(classId: Int) {
        viewModelScope.launch {
            onboardingRepository.getMentorDetail(classId = classId).collect {
                _mentorInfo.value = it
            }
        }
    }

    fun checkUserOnboarding(): Boolean = checkOnboarding

    fun startQuest(classId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            questRepository.startQuest(classId).collect {
                onSuccess()
            }
        }
    }
}