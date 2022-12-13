package com.mityushovn.miningquiz.core_domain.domain.models

/**
 *
 */
data class GameSettings(
    val numberOfExamsQuestions: Float,
    val areAllQuestionsOfExamChosen: Boolean,
    val percentOfRightAnswers: Float
)