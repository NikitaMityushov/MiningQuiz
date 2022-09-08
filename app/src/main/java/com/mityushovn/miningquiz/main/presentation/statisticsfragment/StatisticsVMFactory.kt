package com.mityushovn.miningquiz.main.presentation.statisticsfragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.common.di.qualifiers.ViewModelBackgroundCoroutineDispatcher
import com.mityushovn.miningquiz.common.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.common.domain.repositories.ExamsRepositoryAPI
import com.mityushovn.miningquiz.main.presentation.quizList.examsfragment.ExamsViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Factory for ExamsViewModel.
 * @see ExamsViewModel
 * @see ExamsRepositoryAPI
 */
class StatisticsVMFactory @Inject constructor (
    val attemptsRepository: AttemptsRepositoryAPI,
    @ViewModelBackgroundCoroutineDispatcher
    val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default,
    val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return StatisticsViewModel(attemptsRepository, backgroundDispatcher, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}