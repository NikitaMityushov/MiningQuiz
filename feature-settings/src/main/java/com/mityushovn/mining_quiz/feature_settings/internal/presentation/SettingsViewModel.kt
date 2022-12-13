package com.mityushovn.mining_quiz.feature_settings.internal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.core_domain.domain.repositories.SettingsRepositoryAPI
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

//private const val TAG = "SettingsViewModel"

/**
 *
 */
@OptIn(FlowPreview::class)
internal class SettingsViewModel(
    private val repository: SettingsRepositoryAPI
) : ViewModel() {
    companion object {
        private const val DEBOUNCE_TIME_MS = 600L
    }

    // 1) Exams questions block
    private val _examQuestions =
        MutableStateFlow<Float>(repository.gameSettings.numberOfExamsQuestions)

    fun setExamNumberQuestions(input: Float) {
        _examQuestions.value = input
    }

    fun getStartExamNumberQuestions(): Float {
        return repository.gameSettings.numberOfExamsQuestions
    }

    private val _allQuestionsAreChosen =
        MutableStateFlow<Boolean>(repository.gameSettings.areAllQuestionsOfExamChosen)

    fun setAllQuestionsAreChosen(input: Boolean) {
        _allQuestionsAreChosen.value = input
    }

    fun getStartAllQuestionsAreChosen(): Boolean {
        return repository.gameSettings.areAllQuestionsOfExamChosen
    }

    // 2) Percent of right answers block
    private val _percentOfRightAnswers =
        MutableStateFlow<Float>(repository.gameSettings.percentOfRightAnswers)

    fun setPercentOfRightAnswers(input: Float) {
        _percentOfRightAnswers.value = input
    }

    fun getStartPercentOfRightAnswers(): Float {
        return repository.gameSettings.percentOfRightAnswers
    }

    // 3) Dark mode block
    private val _isDarkMode = MutableStateFlow<Boolean>(repository.isDarkMode)

    fun setDarkMode(input: Boolean) {
        _isDarkMode.value = input
    }

    fun getDarkMode(): Boolean {
        return repository.isDarkMode
    }

    init {
        collectExamsQuestions()
        collectDarkMode()
        collectAllQuestionsAreChosen()
        collectPercentOfRightAnswers()
    }

    private fun collectPercentOfRightAnswers() {
        viewModelScope.launch {
            _percentOfRightAnswers
                .debounce(DEBOUNCE_TIME_MS)
                .distinctUntilChanged()
                .collect { percent: Float ->
                    repository.gameSettings =
                        repository.gameSettings.copy(percentOfRightAnswers = percent)
                }
        }
    }

    private fun collectAllQuestionsAreChosen() {
        viewModelScope.launch {
            _allQuestionsAreChosen
                .debounce(DEBOUNCE_TIME_MS)
                .distinctUntilChanged()
                .collect { areAllChosen: Boolean ->
                    repository.gameSettings =
                        repository.gameSettings.copy(areAllQuestionsOfExamChosen = areAllChosen)
                }
        }
    }

    private fun collectDarkMode() {
        viewModelScope.launch {
            _isDarkMode
                .debounce(DEBOUNCE_TIME_MS)
                .distinctUntilChanged()
                .collect { isDarkMode: Boolean ->
                    repository.isDarkMode = isDarkMode
                }
        }
    }

    /*
        private block
     */
    private fun collectExamsQuestions() {
        viewModelScope.launch {
            _examQuestions
                .debounce(DEBOUNCE_TIME_MS)
                .distinctUntilChanged()
                .collect { number: Float ->
                    repository.gameSettings =
                        repository.gameSettings.copy(numberOfExamsQuestions = number)
                }
        }
    }
}