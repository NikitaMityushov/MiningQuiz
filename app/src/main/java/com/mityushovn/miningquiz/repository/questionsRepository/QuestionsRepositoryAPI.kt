package com.mityushovn.miningquiz.repository.questionsRepository

import com.mityushovn.miningquiz.models.*
import kotlinx.coroutines.flow.Flow

/**
 * @author Nikita Mityushov 11.04.22
 * @since 1.0
 * Interface for repositories of Question entity.
 * @see Question
 */
interface QuestionsRepositoryAPI {

    /**
     * @return Flow with List of all Question class instances, which were wrong answered.
     * @see Question
     */
    suspend fun getAllQuestionsWithWrongAnswers(): Flow<List<Question>>

    /**
     * @param topicId from Topic class instance.
     * @return Flow with List of all Question class instances from specified Topic.
     * @see Question
     * @see Topic
     */
    suspend fun getAllQuestionsFromTopic(topicId: Int): Flow<List<Question>>

    /**
     * Inserts the specified instance of WrongAnswered class to the storage.
     * @param wrongAnswered is instance of WrongAnswered class.
     * @see WrongAnswered
     * @return Flow with a boolean value that indicates success of the operation.
     */
    suspend fun insertWrongAnsweredQuestion(wrongAnswered: WrongAnswered): Flow<Boolean>

    /**
     * @param examId
     * @param numberOfQuestions of the specified exam.
     * @return Flow with List of random specified Question class instance
     * @see Question
     */
    suspend fun getRandomQuestionsFromExamIdAndNumberOfQuestions(
        examId: Int,
        numberOfQuestions: Int = 10
    ): Flow<List<Question>>

    /**
     * This method should matches all inputs from search EditText View to the content field
     * of Question class instance.
     * @param input is a String instance from a search EditText View.
     * @return Flow with List of Question instances.
     * @see Question
     */
    suspend fun getQuestionsMatchesSearchInput(input: String): Flow<List<Question>>

}