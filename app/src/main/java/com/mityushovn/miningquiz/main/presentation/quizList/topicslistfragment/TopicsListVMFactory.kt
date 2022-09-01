package com.mityushovn.miningquiz.main.presentation.quizList.topicslistfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.common.domain.repositories.TopicsRepositoryAPI
import javax.inject.Inject

/**
 * Factory for ExamsViewModel.
 * @see TopicsListViewModel
 * @see TopicsRepositoryAPI
 */
class TopicsListVMFactory @Inject constructor(
    val topicsRepository: TopicsRepositoryAPI
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopicsListViewModel::class.java)) {
            return TopicsListViewModel(topicsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}