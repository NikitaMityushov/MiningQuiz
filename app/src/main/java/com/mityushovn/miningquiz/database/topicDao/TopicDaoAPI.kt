package com.mityushovn.miningquiz.database.topicDao

import com.mityushovn.miningquiz.models.domain.Topic
import kotlinx.coroutines.flow.Flow

/**
 * @author Nikita Mityushov 8.04.22
 * @since 1.0
 * Interface for the topic table access.
 */
interface TopicDaoAPI {

    /**
     * @return Flow with List of all Topic class instances.
     * @see Topic
     */
    suspend fun getAllTopics(): Flow<List<Topic>>
}