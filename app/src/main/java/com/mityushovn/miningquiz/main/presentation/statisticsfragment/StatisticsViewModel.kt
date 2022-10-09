package com.mityushovn.miningquiz.main.presentation.statisticsfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.common.MiningQuizApplication
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.common.domain.models.statisticsEntities.AbstractStatistics
import com.mityushovn.miningquiz.common.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.utils.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

private const val NUMBER_OF_RETRIES = 1L

/**
 * @author Nikita Mityushov 27.04.22
 * @since 1.0
 * ViewModel class to store state for a StatisticsFragment.
 * @see StatisticsFragment
 */
class StatisticsViewModel(
    private val attemptsRepository: AttemptsRepositoryAPI,
    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default,
    application: Application
) : AndroidViewModel(application) {

    // prepared strings:
    // exams
    private val _stringAttemptsExam = MutableStateFlow("")
    val stringAttemptsExam: StateFlow<String>
        get() = _stringAttemptsExam

    // topics
    private val _stringAttemptsTopic = MutableStateFlow("")
    val stringAttemptsTopic: StateFlow<String>
        get() = _stringAttemptsTopic

    // loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    // error state
    private val _showErrorMessage = MutableStateFlow(false)
    val showErrorMessage: StateFlow<Boolean>
        get() = _showErrorMessage

    // successfully or not deleted stats event
    private val _eventDeletedStats = MutableStateFlow<Event<Boolean>>(Event())
    val eventDeletedStats: LiveEvent<Boolean> = _eventDeletedStats.share()

    init {
        updateStats()
    }

    fun deleteAllStatistics() {
        viewModelScope.launch {
            customRetryFlow(NUMBER_OF_RETRIES) {
                attemptsRepository.deleteAllStatistics()
            }.catch {
                Timber.e(it)
                _eventDeletedStats.postEvent(false)
            }.collect {
                _eventDeletedStats.postEvent(it)
            }
        }
    }

    fun updateStats() {
        zipExamsAndTopicsStatisticsAndPrepareStrings()
    }

    private fun zipExamsAndTopicsStatisticsAndPrepareStrings() {
        viewModelScope.launch {
            customRetryFlow(NUMBER_OF_RETRIES) {
                attemptsRepository
                    .getExamsStatistics()
                    .map { prepareStringAttemptsExam(it) }
                    .zip(
                        attemptsRepository
                            .getTopicsStatistics()
                            .map { prepareStringAttemptsTopic(it) }
                    ) { exam, topic -> exam to topic }
            }
                .catch {
                    Timber.e(it)
                    _showErrorMessage.value = true
                    _isLoading.value = false
                }
                .flowOn(backgroundDispatcher)
                .onStart {
                    _isLoading.value = true
                    _showErrorMessage.value = false
                }
                .collect { (examString, topicString) ->
                    _stringAttemptsExam.value = examString
                    _stringAttemptsTopic.value = topicString
                    _isLoading.value = false
                }
        }
    }

    private fun prepareStringAttemptsExam(it: AbstractStatistics): String {
        return String.format(
            getApplication<MiningQuizApplication>().resources.getString(R.string.statistics_exam_string),
            it.numberOfAttempts,
            it.numberOfSuccessAttempts,
            it.numberOfFailedAttempts
        )
    }

    private fun prepareStringAttemptsTopic(it: AbstractStatistics): String {
        return String.format(
            getApplication<MiningQuizApplication>().resources.getString(R.string.statistics_topic_string),
            it.numberOfAttempts,
            it.numberOfSuccessAttempts,
            it.numberOfFailedAttempts
        )
    }
}

