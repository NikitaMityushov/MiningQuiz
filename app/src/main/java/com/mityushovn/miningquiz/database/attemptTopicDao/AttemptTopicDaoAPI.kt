package com.mityushovn.miningquiz.database.attemptTopicDao

import com.mityushovn.miningquiz.models.AttemptTopic
import kotlinx.coroutines.flow.Flow

/**
 * @author Nikita Mityushov 8.04.22
 * @since 1.0
 * Interface for the attempt_topic table access.
 */
interface AttemptTopicDaoAPI {

    /**
     * Inserts an attempt of Topic solving to the storage.
     * @param attemptTopic is an instance of AttemptTopic class.
     * @see AttemptTopic
     * @return Flow with a boolean value that indicates success of the operation.
     */
    suspend fun insertAttemptTopic(attemptTopic: AttemptTopic): Flow<Boolean>

    /**
     * @return number of all solved topics.
     */
    suspend fun getNumberOfTopicSolvingAttempts(): Int

    /**
     * @return number of successful solved topics.
     */
    suspend fun getNumberOfSuccessfulTopicSolvingAttempts(): Int

    /**
     * @return number of failed topics.
     */
    suspend fun getNumberOfFailedTopicSolvingAttempts(): Int
}