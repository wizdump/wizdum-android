package com.teamwizdum.wizdum.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwizdum.wizdum.data.model.KeywordResponse
import com.teamwizdum.wizdum.data.model.QuestionResponse
import com.teamwizdum.wizdum.data.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: OnboardingRepository,
) : ViewModel() {

    private val _keywords = MutableStateFlow<List<KeywordResponse>>(emptyList())
    val keywords: StateFlow<List<KeywordResponse>> = _keywords

    private val _questions = MutableStateFlow<List<QuestionResponse>>(emptyList())
    val questions: StateFlow<List<QuestionResponse>> = _questions


    fun getKeyword(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            repository.getKeywords().collect {
                _keywords.value = it
                onSuccess()
            }
        }
    }

    fun getQuestion(keywordId: Int) {
        viewModelScope.launch {
            repository.getQuestions(keywordId).collect {
                _questions.value = it
            }
        }
    }
}