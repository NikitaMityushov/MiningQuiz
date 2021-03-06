package com.mityushovn.miningquiz.activities.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.repository.questionsRepository.QuestionsRepositoryAPI
import javax.inject.Inject

/**
 * @author Nikita Mityushov
 * @since 1.0
 * @see QuestionsRepositoryAPI
 */
class MainActivityVMFactory @Inject constructor(
    val questionsRepository: QuestionsRepositoryAPI
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(questionsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}