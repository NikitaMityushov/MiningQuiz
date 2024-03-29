package com.mityushovn.miningquiz.data_questions.internal.repositories

import com.mityushovn.miningquiz.core_domain.domain.models.Question
import com.mityushovn.miningquiz.core_domain.domain.models.WrongAnswered
import com.mityushovn.miningquiz.core_domain.domain.repositories.QuestionsRepositoryAPI
import com.mityushovn.miningquiz.data_questions.internal.questionsDao.QuestionsDaoAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.*

/**
 * @author Nikita Mityushov 11.04.22
 * @since 1.0
 * QuestionsRepositoryAPI implementation.
 * @see QuestionsRepositoryAPI
 */
internal class QuestionsRepository(
    private val questionsDao: QuestionsDaoAPI,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : QuestionsRepositoryAPI {

    /**
     * @see QuestionsRepositoryAPI.getAllQuestionsWithWrongAnswers
     */
    override suspend fun getAllQuestionsWithWrongAnswers(): Flow<Deque<Question>> {
        return questionsDao.getAllQuestionsWithWrongAnswers().flowOn(coroutineDispatcher)
    }

    /**
     * @see QuestionsRepositoryAPI.getAllQuestionsFromTopic
     */
    override suspend fun getAllQuestionsFromTopic(topicId: Int): Flow<Deque<Question>> {
        return questionsDao.getAllQuestionsFromTopic(topicId).flowOn(coroutineDispatcher)
    }

    /**
     * @see QuestionsRepositoryAPI.insertWrongAnsweredQuestion
     */
    override suspend fun insertWrongAnsweredQuestion(wrongAnswered: WrongAnswered): Flow<Boolean> {
        return questionsDao.insertWrongAnsweredQuestion(wrongAnswered).flowOn(coroutineDispatcher)
    }

    /**
     * @see QuestionsRepositoryAPI.getRandomQuestionsFromExamIdAndNumberOfQuestions
     */
    override suspend fun getRandomQuestionsFromExamIdAndNumberOfQuestions(
        examId: Int,
        numberOfQuestions: Int
    ): Flow<Deque<Question>> {
        return questionsDao.getRandomQuestionsFromExamIdAndNumberOfQuestions(
            examId,
            numberOfQuestions
        ).flowOn(coroutineDispatcher)
    }

    /**
     * @see QuestionsRepositoryAPI.getQuestionsMatchesSearchInput
     */
    override suspend fun getQuestionsMatchesSearchInput(input: String): Flow<List<Question>> {
        return questionsDao.getQuestionsMatchesSearchInput(input).flowOn(coroutineDispatcher)
    }

    /**
     * @see QuestionsDaoAPI.getQuestionById
     */
    override suspend fun getQuestionById(questionId: Int): Flow<Question> {
        return questionsDao.getQuestionById(questionId).flowOn(coroutineDispatcher)
    }

}