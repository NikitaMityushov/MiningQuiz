package com.mityushovn.miningquiz.core_domain.domain.repositories

/**
 *  @author Nikita Mityushov 29.11.22
 *  @since 1.0.4
 *  Represents API to provide settings of the application.
 */
interface SettingsRepositoryApi {
    /**
     * Represent number of questions in the EXAM mode of quiz.
     */
    var examsQuestionsNumber: Int

    /**
     *  Represent number of questions in the TOPIC mode of quiz.
     */
    var topicsQuestionsNumber: Int

    /**
     * Represents dark mode toggle
     */
    var isDarkMode: Boolean
}
