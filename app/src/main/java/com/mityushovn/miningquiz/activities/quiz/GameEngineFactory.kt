package com.mityushovn.miningquiz.activities.quiz

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushovn.miningquiz.repository.attemptsRepository.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.repository.questionsRepository.QuestionsRepositoryAPI
import javax.inject.Inject

/**
 * @author Nikita Mityushov
 * @since 1.0
 * @see QuestionsRepositoryAPI
 */
class GameEngineFactory @Inject constructor(
    val questionsRepository: QuestionsRepositoryAPI,
    val attemptsRepository: AttemptsRepositoryAPI,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameEngine::class.java)) {
            return GameEngine(questionsRepository, attemptsRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}