package com.mityushovn.miningquiz.game_feature.internal.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.core_domain.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.core_domain.domain.repositories.QuestionsRepositoryAPI
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * @author Nikita Mityushov
 * @since 1.0
 * @see QuestionsRepositoryAPI
 */
@Suppress("UNCHECKED_CAST")
internal class GameEngineFactory @Inject constructor(
    private val questionsRepository: QuestionsRepositoryAPI,
    private val attemptsRepository: AttemptsRepositoryAPI,
    private val backgroundDispatcher: CoroutineDispatcher,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameEngine::class.java)) {
            return GameEngine(
                questionsRepository,
                attemptsRepository,
                backgroundDispatcher,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}