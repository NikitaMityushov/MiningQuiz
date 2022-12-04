package com.mityushovn.mining_quiz.feature_settings.internal.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.core_domain.domain.repositories.SettingsRepositoryApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val TAG = "SettingsViewModel"

/**
 *
 */
@OptIn(FlowPreview::class)
internal class SettingsViewModel(
    private val repository: SettingsRepositoryApi
) : ViewModel() {
    companion object {
        private const val DEFAULT_NUMBER = 10
        private const val DEBOUNCE_TIME_MS = 600L
    }

    private val _examQuestions = MutableStateFlow(DEFAULT_NUMBER)

    fun setExamNumberQuestions(input: Int) {
        _examQuestions.value = input
    }

    private val _topicQuestions = MutableStateFlow(DEFAULT_NUMBER)

    fun setTopicNumberQuestions(input: Int) {
        _topicQuestions.value = input
    }


    init {
        collectExamsQuestions()
        collectTopicQuestions()
    }

    /*
        private block
     */
    private fun collectExamsQuestions() {
        viewModelScope.launch {
            _examQuestions
                .debounce(DEBOUNCE_TIME_MS)
                .distinctUntilChanged()
                .onEach {
                    Log.d(TAG, "exams value is $it")
                }
                .collect { number: Int ->
                    repository.examsQuestionsNumber = number
                }
        }
    }

    private fun collectTopicQuestions() {
        viewModelScope.launch {
            _examQuestions
                .debounce(DEBOUNCE_TIME_MS)
                .distinctUntilChanged()
                .onEach {
                    Log.d(TAG, "topics value is $it")
                }
                .collect { number: Int ->
                    repository.examsQuestionsNumber = number
                }
        }
    }
}