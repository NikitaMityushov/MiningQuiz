package com.mityushovn.miningquiz.repository.attemptsRepository

import com.mityushovn.miningquiz.models.*
import com.mityushovn.miningquiz.models.statisticsEntities.AbstractStatistics
import kotlinx.coroutines.flow.Flow

/**
 * @author Nikita Mityushov 11.04.22
 * @since 1.0
 * Interface for repositories of AttemptExam and AttemptTopic entities.
 * @see Exam
 */
interface AttemptsRepositoryAPI {

    /**
     * Inserts an attempt of Topic solving to the storage.
     * @param attemptTopic is an instance of AttemptTopic class.
     * @see AttemptTopic
     * @return Flow with a boolean value that indicates success of the operation.
     */
    suspend fun insertAttemptTopic(attemptTopic: AttemptTopic): Flow<Boolean>

    /**
     * Inserts an attempt of Exam solving to the storage.
     * @param attemptExam is an instance of AttemptExam class.
     * @see AttemptExam
     * @return Flow with a boolean value that indicates success of the operation.
     */
    suspend fun insertAttemptExam(attemptExam: AttemptExam): Flow<Boolean>

    /**
     * @return Flow with an AbstractStatistics instance with info about solving exams
     * throughout the entire period of using Application.
     * @see AbstractStatistics
     */
    suspend fun getExamsStatistics(): Flow<AbstractStatistics>

    /**
     * @return Flow with an AbstractStatistics instance with info about solving topics
     * throughout the entire period of using Application.
     * @see AbstractStatistics
     */
    suspend fun getTopicsStatistics(): Flow<AbstractStatistics>

    /**
     * Deletes all attempts of topic solving and exam solving.
     */
    suspend fun deleteAllStatistics(): Flow<Boolean>

}