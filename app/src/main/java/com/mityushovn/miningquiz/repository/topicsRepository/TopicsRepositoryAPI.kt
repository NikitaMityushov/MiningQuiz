package com.mityushovn.miningquiz.repository.topicsRepository

import com.mityushovn.miningquiz.models.domain.Topic
import kotlinx.coroutines.flow.Flow

/**
 * @author Nikita Mityushov 12.04.22
 * @since 1.0
 * Interface for repositories of Topic entity.
 * @see Topic
 */
interface TopicsRepositoryAPI {

    /**
     * @return Flow with List of all Topic class instances.
     * @see Topic
     */
    suspend fun getAllTopics(): Flow<List<Topic>>

}