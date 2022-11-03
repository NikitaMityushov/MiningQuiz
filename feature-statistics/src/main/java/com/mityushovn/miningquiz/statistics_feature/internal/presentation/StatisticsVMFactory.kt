package com.mityushovn.miningquiz.statistics_feature.internal.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.core_domain.domain.repositories.AttemptsRepositoryAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Factory for StatisticsViewModel.
 * @see StatisticsViewModel
 * @see AttemptsRepositoryAPI
 */
@Suppress("UNCHECKED_CAST")
class StatisticsVMFactory @Inject constructor(
    private val attemptsRepository: AttemptsRepositoryAPI,
    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return StatisticsViewModel(attemptsRepository, backgroundDispatcher, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}