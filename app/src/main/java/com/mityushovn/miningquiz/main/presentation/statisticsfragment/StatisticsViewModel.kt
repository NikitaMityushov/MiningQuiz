package com.mityushovn.miningquiz.main.presentation.statisticsfragment

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.common.MiningQuizApplication
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.common.domain.repositories.AttemptsRepositoryAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Nikita Mityushov 27.04.22
 * @since 1.0
 * ViewModel class to store state for a StatisticsFragment.
 * @see StatisticsFragment
 */
class StatisticsViewModel(
    private val attemptsRepository: AttemptsRepositoryAPI,
    application: Application
) : AndroidViewModel(application) {

    // prepared strings:
    // exams
    private val _stringAttemptsExam = MutableLiveData("")
    val stringAttemptsExam: LiveData<String>
        get() = _stringAttemptsExam

    // topics
    private val _stringAttemptsTopic = MutableLiveData("")
    val stringAttemptsTopic: LiveData<String>
        get() = _stringAttemptsTopic

    init {
        prepareStrings()
    }

    fun deleteAllStatistics() {
        viewModelScope.launch {
            Timber.d("deleteAllStatistics")
            Timber.d(Thread.currentThread().name)
            attemptsRepository.deleteAllStatistics().collect {
                showIfDeletedStatToast(it)
            }

        }
    }


    private fun prepareStrings() {
        Timber.d("prepareStrings")
        with(viewModelScope) {
            // 1) request exams statistics
            launch(Dispatchers.Default) {
                attemptsRepository
                    .getExamsStatistics()
                    .map {
                        String.format(
                            getApplication<MiningQuizApplication>().resources.getString(R.string.statistics_exam_string),
                            it.numberOfAttempts,
                            it.numberOfSuccessAttempts,
                            it.numberOfFailedAttempts
                        )
                    }
                    .collect {
                        Timber.d("Collect, exam string is $it")
                        _stringAttemptsExam.postValue(it)
                    }
            }

            // request topics statistics
            launch(Dispatchers.Default) {
                attemptsRepository
                    .getTopicsStatistics()
                    .map {
                        String.format(
                            getApplication<MiningQuizApplication>().resources.getString(R.string.statistics_topic_string),
                            it.numberOfAttempts,
                            it.numberOfSuccessAttempts,
                            it.numberOfFailedAttempts
                        )
                    }
                    .collect {
                        Timber.d("Collect, topic string is $it")
                        _stringAttemptsTopic.postValue(it)
                    }
            }
        }
    }

    private fun showIfDeletedStatToast(it: Boolean) {
        if (it) {
            Toast.makeText(
                getApplication<MiningQuizApplication>(),
                getApplication<MiningQuizApplication>().resources.getString(R.string.stat_deleted_successfuly),
                Toast.LENGTH_SHORT
            ).show()
            prepareStrings() // if deleted successfully, you need to refresh prepared strings
        } else {
            Toast.makeText(
                getApplication<MiningQuizApplication>(),
                getApplication<MiningQuizApplication>().resources.getString(R.string.stat_deleted_failed),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

