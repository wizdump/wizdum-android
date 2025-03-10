package com.teamwizdum.wizdum.feature.quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.response.LectureResponse
import com.teamwizdum.wizdum.data.repository.QuestRepository
import com.teamwizdum.wizdum.data.repository.RewardRepository
import com.teamwizdum.wizdum.feature.quest.info.ChatRoomInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LectureViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val rewardRepository: RewardRepository
) : ViewModel() {

    private val _lectureInfo = MutableStateFlow<LectureResponse>(LectureResponse())
    val lectureInfo: StateFlow<LectureResponse> = _lectureInfo

    var chatRoomInfo = ChatRoomInfo()

    fun getQuest(classId: Int) {
        viewModelScope.launch {
            questRepository.getQuests(classId).collect {
                _lectureInfo.value = it
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

    fun updateChatRoomInfo(info: ChatRoomInfo) {
        chatRoomInfo = info
    }
}