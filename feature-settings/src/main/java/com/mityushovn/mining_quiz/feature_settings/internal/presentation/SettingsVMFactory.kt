package com.mityushovn.mining_quiz.feature_settings.internal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.core_domain.domain.repositories.SettingsRepositoryAPI
import javax.inject.Inject

/**
 *
 */
@Suppress("UNCHECKED_CAST")
internal class SettingsVMFactory @Inject constructor(private val repository: SettingsRepositoryAPI) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}