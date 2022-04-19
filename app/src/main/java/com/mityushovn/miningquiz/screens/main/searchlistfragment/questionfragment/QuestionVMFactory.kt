package com.mityushovn.miningquiz.screens.main.searchlistfragment.questionfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.repository.questionsRepository.QuestionsRepositoryAPI

/**
 * @author Nikita Mityushov
 * @since 1.0
 * Factory for QuestionViewModel creation.
 * @see QuestionViewModel
 */
class QuestionVMFactory(
    private val questionId: Int,
    private val questionsRepository: QuestionsRepositoryAPI
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            return QuestionViewModel(questionId, questionsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}