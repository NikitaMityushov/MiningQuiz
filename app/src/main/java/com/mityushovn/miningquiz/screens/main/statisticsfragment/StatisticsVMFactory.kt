package com.mityushovn.miningquiz.screens.main.statisticsfragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.MiningQuizApplication
import com.mityushovn.miningquiz.repository.attemptsRepository.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.repository.examsRepository.ExamsRepositoryAPI
import com.mityushovn.miningquiz.screens.main.quizList.examsfragment.ExamsViewModel

/**
 * Factory for ExamsViewModel.
 * @see ExamsViewModel
 * @see ExamsRepositoryAPI
 */
class StatisticsVMFactory(
    private val attemptsRepository: AttemptsRepositoryAPI,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return StatisticsViewModel(attemptsRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}