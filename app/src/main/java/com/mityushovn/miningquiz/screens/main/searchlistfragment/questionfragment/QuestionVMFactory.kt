package com.mityushovn.miningquiz.screens.main.searchlistfragment.questionfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.repository.questionsRepository.QuestionsRepositoryAPI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * @author Nikita Mityushov
 * @since 1.0
 * Factory for QuestionViewModel creation.
 * @see QuestionViewModel
 */
class QuestionVMFactory @AssistedInject constructor(
    @Assisted
    val questionId: Int,
    val questionsRepository: QuestionsRepositoryAPI
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            return QuestionViewModel(questionId, questionsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@AssistedFactory
interface QuestionVMFactoryAssistedFactory {
    fun create(questionId: Int): QuestionVMFactory
}