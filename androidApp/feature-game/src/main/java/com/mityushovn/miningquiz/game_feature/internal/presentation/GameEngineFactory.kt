package com.mityushovn.miningquiz.game_feature.internal.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.core_domain.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.core_domain.domain.repositories.QuestionsRepositoryAPI
import com.mityushovn.miningquiz.core_domain.domain.repositories.SettingsRepositoryAPI
import com.mityushovn.miningquiz.game_feature.api.GameMode
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
    private val settingsRepository: SettingsRepositoryAPI,
    private val backgroundDispatcher: CoroutineDispatcher,
    private val gameMode: GameMode,
    private val examOrTopicId: Int,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameEngine::class.java)) {
            return GameEngine(
                questionsRepository,
                attemptsRepository,
                settingsRepository,
                backgroundDispatcher,
                gameMode,
                examOrTopicId,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}