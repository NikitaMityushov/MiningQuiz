package com.mityushovn.miningquiz.repository.attemptsRepository

import com.mityushovn.miningquiz.database.attemptExamDao.AttemptExamDaoAPI
import com.mityushovn.miningquiz.database.attemptTopicDao.AttemptTopicDaoAPI
import com.mityushovn.miningquiz.models.*
import com.mityushovn.miningquiz.models.statisticsEntities.AbstractStatistics
import com.mityushovn.miningquiz.models.statisticsEntities.ExamsSolvingStatistics
import com.mityushovn.miningquiz.models.statisticsEntities.TopicsSolvingStatistics
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip

/**
 * @author Nikita Mityushov 11.04.22
 * @since 1.0
 * AttemptsRepositoryAPI implementation.
 * @see AttemptsRepositoryAPI
 */
class AttemptsRepository(
    private val attemptExamDao: AttemptExamDaoAPI,
    private val attemptTopicDao: AttemptTopicDaoAPI,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AttemptsRepositoryAPI {

    private val repositoryCoroutineScope =
        CoroutineScope(SupervisorJob() + coroutineDispatcher) // private repository coroutine scope

    /**
     * @see AttemptsRepositoryAPI.insertAttemptTopic
     */
    override suspend fun insertAttemptTopic(attemptTopic: AttemptTopic): Flow<Boolean> {
        return attemptTopicDao.insertAttemptTopic(attemptTopic).flowOn(coroutineDispatcher)
    }

    /**
     * @see AttemptsRepositoryAPI.insertAttemptExam
     */
    override suspend fun insertAttemptExam(attemptExam: AttemptExam): Flow<Boolean> {
        return attemptExamDao.insertAttemptExam(attemptExam).flowOn(coroutineDispatcher)
    }

    /**
     * @see AttemptsRepositoryAPI.getExamsStatistics
     */
    override suspend fun getExamsStatistics(): Flow<AbstractStatistics> = flow {
        emit(
            ExamsSolvingStatistics(
                numberOfAttempts = attemptExamDao.getNumberOfExamSolvingAttempts(),
                numberOfSuccessAttempts = attemptExamDao.getNumberOfSuccessfulExamSolvingAttempts(),
                numberOfFailedAttempts = attemptExamDao.getNumberOfFailedExamSolvingAttempts()
            )
        )
    }.flowOn(coroutineDispatcher)

    /**
     * @see AttemptsRepositoryAPI.getTopicsStatistics
     */
    override suspend fun getTopicsStatistics(): Flow<AbstractStatistics> = flow {
        emit(
            TopicsSolvingStatistics(
                numberOfAttempts = attemptTopicDao.getNumberOfTopicSolvingAttempts(),
                numberOfSuccessAttempts = attemptTopicDao.getNumberOfSuccessfulTopicSolvingAttempts(),
                numberOfFailedAttempts = attemptTopicDao.getNumberOfFailedTopicSolvingAttempts()
            )
        )
    }.flowOn(coroutineDispatcher)

    /**
     * @see AttemptsRepositoryAPI.deleteAllStatistics
     */
    override suspend fun deleteAllStatistics(): Flow<Boolean> {
        return attemptExamDao.deleteAllExamsAttempts()
            .zip(attemptTopicDao.deleteAllTopicAttempts()) { b1, b2 ->
                b1 && b2
            }.flowOn(coroutineDispatcher)
    }
}