package com.mityushovn.miningquiz.common.data.local.repositories.questionsRepository

import com.mityushovn.miningquiz.common.data.local.database.questionsDao.QuestionsDaoAPI
import com.mityushovn.miningquiz.common.di.qualifiers.RepositoryCoroutineDispatcher
import com.mityushovn.miningquiz.common.di.scopes.AppScope
import com.mityushovn.miningquiz.common.domain.models.Question
import com.mityushovn.miningquiz.common.domain.models.WrongAnswered
import com.mityushovn.miningquiz.common.domain.repositories.QuestionsRepositoryAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

/**
 * @author Nikita Mityushov 11.04.22
 * @since 1.0
 * QuestionsRepositoryAPI implementation.
 * @see QuestionsRepositoryAPI
 */
@AppScope
class QuestionsRepository @Inject constructor (
    val questionsDao: QuestionsDaoAPI,
    @RepositoryCoroutineDispatcher
    val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
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