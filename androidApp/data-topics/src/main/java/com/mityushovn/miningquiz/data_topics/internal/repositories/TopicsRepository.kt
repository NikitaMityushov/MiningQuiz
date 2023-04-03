package com.mityushovn.miningquiz.data_topics.internal.repositories

import com.mityushovn.miningquiz.core_domain.domain.models.Topic
import com.mityushovn.miningquiz.core_domain.domain.repositories.TopicsRepositoryAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * @author Nikita Mityushov 12.04.22
 * @since 1.0
 * TopicsRepositoryAPI implementation.
 * @see TopicsRepositoryAPI
 */
internal class TopicsRepository(
    private val topicDao: com.mityushovn.miningquiz.data_topics.internal.topicDao.TopicDaoAPI,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TopicsRepositoryAPI {

    /**
     * @see TopicsRepositoryAPI.getAllTopics
     */
    override suspend fun getAllTopics(): Flow<List<Topic>> {
        return topicDao.getAllTopics().flowOn(coroutineDispatcher)
    }
}