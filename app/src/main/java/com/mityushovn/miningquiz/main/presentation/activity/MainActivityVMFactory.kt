package com.mityushovn.miningquiz.main.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.core_domain.domain.repositories.QuestionsRepositoryAPI
import javax.inject.Inject

/**
 * @author Nikita Mityushov
 * @since 1.0
 * @see QuestionsRepositoryAPI
 */
@Suppress("UNCHECKED_CAST")
class MainActivityVMFactory @Inject constructor(
    private val questionsRepository: QuestionsRepositoryAPI
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(questionsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}