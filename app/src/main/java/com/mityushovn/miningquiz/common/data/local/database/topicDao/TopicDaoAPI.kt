package com.mityushovn.miningquiz.common.data.local.database.topicDao

import com.mityushovn.miningquiz.common.domain.models.Topic
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