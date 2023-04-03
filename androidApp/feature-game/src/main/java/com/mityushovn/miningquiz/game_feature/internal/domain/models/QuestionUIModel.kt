package com.mityushovn.miningquiz.game_feature.internal.domain.models

import com.mityushovn.miningquiz.core_domain.domain.models.Question
import kotlin.random.Random
import com.mityushovn.miningquiz.game_feature.internal.presentation.gamefragment.GameFragment

/**
 * Represents UI content for GameFragment.
 * @see GameFragment
 */
class QuestionUIModel(
    val nameTopic: String,
    val nameExam: String,
    val content: String,
    val rightAns: String,
    val ans1: String,
    val ans2: String,
    val ans3: String,
    val ans4: String,
    val index: Int // index of current answer in the quiz
)

/**
 * Extension function for transforming Question to QuestionUIModel
 * @see Question
 */
internal fun Question.toQuestionUIModel(index: Int): QuestionUIModel {
    val array: Array<String> =
        arrayOf(
            this.ans1,
            this.ans2,
            this.ans3,
            this.rightAns
        )
    array.shuffle(Random(System.currentTimeMillis() + index)) // рандомайзер, варианты ответов должны быть на рандомных позициях

    return QuestionUIModel(
        nameTopic = this.nameTopic,
        nameExam = this.nameExam,
        content = this.content,
        rightAns = this.rightAns,
        ans1 = array[0],
        ans2 = array[1],
        ans3 = array[2],
        ans4 = array[3],
        index = index
    )
}