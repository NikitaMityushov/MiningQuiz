package com.mityushovn.quizlist_feature.internal.presentation.examsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.core_domain.domain.repositories.ExamsRepositoryAPI
import javax.inject.Inject

/**
 * Factory for ExamsViewModel.
 * @see ExamsViewModel
 * @see ExamsRepositoryAPI
 */
internal class ExamsVMFactory @Inject constructor(
    private val examsRepository: ExamsRepositoryAPI
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExamsViewModel::class.java)) {
            return ExamsViewModel(examsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}