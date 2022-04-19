package com.mityushovn.miningquiz.screens.main.quizList.examsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.repository.examsRepository.ExamsRepositoryAPI

/**
 * Factory for ExamsViewModel.
 * @see ExamsViewModel
 * @see ExamsRepositoryAPI
 */
class ExamsVMFactory(
    private val examsRepository: ExamsRepositoryAPI
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExamsViewModel::class.java)) {
            return ExamsViewModel(examsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}