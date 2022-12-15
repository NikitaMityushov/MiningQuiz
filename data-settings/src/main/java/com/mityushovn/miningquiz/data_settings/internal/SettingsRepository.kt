package com.mityushovn.miningquiz.data_settings.internal

import android.content.SharedPreferences
import com.mityushovn.miningquiz.core_domain.domain.models.GameSettings
import com.mityushovn.miningquiz.core_domain.domain.repositories.SettingsRepositoryAPI

private const val EXAMS_Q_NUMBER = "EXAMS_QUESTIONS_NUMBER"
private const val DEFAULT_EXAMS_Q_NUMBER = 10f
private const val IS_ALL_QUESTIONS_OF_EXAM_CHOSEN = "ALL_QUESTIONS_OF_EXAM_CHOSEN"
private const val DEFAULT_ALL_QUESTIONS_OF_EXAM_CHOSEN = false
private const val PERCENT_OF_RIGHT_ANS = "PERCENT_OF_RIGHT_ANS"
private const val DEFAULT_PERCENT_OF_RIGHT_ANS = 80.0f
private const val IS_DARK_MODE = "IS_DARK_MODE"
private const val DEFAULT_DARK_MODE = false

/**
 *  [SettingsRepositoryAPI] implementation.
 *  @see SettingsRepositoryAPI
 */
internal class SettingsRepository(val prefs: SharedPreferences) : SettingsRepositoryAPI {

    override var isDarkMode: Boolean
        get() = prefs.getBoolean(IS_DARK_MODE, DEFAULT_DARK_MODE)
        set(value) {
            prefs.edit().putBoolean(IS_DARK_MODE, value).apply()
        }

    override var gameSettings: GameSettings
        get() = getGameSettingsFromPrefs()
        set(value) = setGameSettingsToPrefs(value)

    /**
     * private block
     */
    private fun getGameSettingsFromPrefs(): GameSettings {
        val examsNumber = prefs.getFloat(EXAMS_Q_NUMBER, DEFAULT_EXAMS_Q_NUMBER)
        val isAllQuestionsChosen =
            prefs.getBoolean(
                IS_ALL_QUESTIONS_OF_EXAM_CHOSEN,
                DEFAULT_ALL_QUESTIONS_OF_EXAM_CHOSEN
            )
        val percentOfRightAnswers =
            prefs.getFloat(PERCENT_OF_RIGHT_ANS, DEFAULT_PERCENT_OF_RIGHT_ANS)
        return GameSettings(examsNumber, isAllQuestionsChosen, percentOfRightAnswers)
    }

    private fun setGameSettingsToPrefs(value: GameSettings) {
        prefs.edit().putFloat(EXAMS_Q_NUMBER, value.numberOfExamsQuestions).apply()
        prefs.edit()
            .putBoolean(IS_ALL_QUESTIONS_OF_EXAM_CHOSEN, value.areAllQuestionsOfExamChosen)
            .apply()
        prefs.edit().putFloat(PERCENT_OF_RIGHT_ANS, value.percentOfRightAnswers).apply()
    }
}