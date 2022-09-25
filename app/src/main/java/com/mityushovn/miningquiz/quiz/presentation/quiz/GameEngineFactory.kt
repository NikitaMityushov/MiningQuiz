package com.mityushovn.miningquiz.quiz.presentation.quiz

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.common.di.qualifiers.ViewModelBackgroundCoroutineDispatcher
import com.mityushovn.miningquiz.common.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.common.domain.repositories.QuestionsRepositoryAPI
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * @author Nikita Mityushov
 * @since 1.0
 * @see QuestionsRepositoryAPI
 */
class GameEngineFactory @Inject constructor(
    val questionsRepository: QuestionsRepositoryAPI,
    val attemptsRepository: AttemptsRepositoryAPI,
    @ViewModelBackgroundCoroutineDispatcher
    val backgroundDispatcher: CoroutineDispatcher,
    val application: Application
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