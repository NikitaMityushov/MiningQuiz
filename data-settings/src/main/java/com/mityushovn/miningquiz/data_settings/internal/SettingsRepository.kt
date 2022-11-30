package com.mityushovn.miningquiz.data_settings.internal

import android.content.SharedPreferences
import com.mityushovn.miningquiz.core_domain.domain.repositories.SettingsRepositoryApi

private const val EXAMS_Q_NUMBER = "EXAMS_QUESTIONS_NUMBER"
private const val DEFAULT_EXAMS_Q_NUMBER = 10
private const val TOPICS_Q_NUMBER = "TOPICS_QUESTIONS_NUMBER"
private const val DEFAULT_TOPICS_Q_NUMBER = 10
private const val IS_DARK_MODE = "IS_DARK_MODE"
private const val DEFAULT_DARK_MODE = false

/**
 *  [SettingsRepositoryApi] implementation.
 *  @see SettingsRepositoryApi
 */
internal class SettingsRepository(val prefs: SharedPreferences) : SettingsRepositoryApi {

    override var examsQuestionsNumber: Int
        get() = prefs.getInt(EXAMS_Q_NUMBER, DEFAULT_EXAMS_Q_NUMBER)
        set(value) {
            prefs.edit().putInt(EXAMS_Q_NUMBER, value).apply()
        }

    override var topicsQuestionsNumber: Int
        get() = prefs.getInt(TOPICS_Q_NUMBER, DEFAULT_TOPICS_Q_NUMBER)
        set(value) {
            prefs.edit().putInt(TOPICS_Q_NUMBER, value).apply()
        }

    override var isDarkMode: Boolean
        get() = prefs.getBoolean(IS_DARK_MODE, DEFAULT_DARK_MODE)
        set(value) {
            prefs.edit().putBoolean(IS_DARK_MODE, value).apply()
        }
}