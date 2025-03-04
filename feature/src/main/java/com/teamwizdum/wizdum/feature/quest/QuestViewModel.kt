package com.teamwizdum.wizdum.feature.quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.QuestResponse
import com.teamwizdum.wizdum.data.repository.QuestRepository
import com.teamwizdum.wizdum.data.repository.RewardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val rewardRepository: RewardRepository
) : ViewModel() {

    private val _quests = MutableStateFlow<QuestResponse>(QuestResponse())
    val quests: StateFlow<QuestResponse> = _quests

    fun getQuest(mentorId: Int) {
        viewModelScope.launch {
            questRepository.getQuests(mentorId).collect {
                _quests.value = it
            }
        }
    }

    fun postReward(lectureId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            rewardRepository.postReward(lectureId).collect {
                onSuccess()
            }
        }
    }
}