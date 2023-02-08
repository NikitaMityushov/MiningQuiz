package com.mityushovn.miningquiz.feature_quizlist.internal.presentation.topicslistfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.core_domain.domain.repositories.TopicsRepositoryAPI
import javax.inject.Inject

/**
 * Factory for ExamsViewModel.
 * @see TopicsListViewModel
 * @see TopicsRepositoryAPI
 */
class TopicsListVMFactory @Inject constructor(
    private val topicsRepository: TopicsRepositoryAPI
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopicsListViewModel::class.java)) {
            return TopicsListViewModel(topicsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}