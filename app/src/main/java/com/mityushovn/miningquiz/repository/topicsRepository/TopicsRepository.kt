package com.mityushovn.miningquiz.repository.topicsRepository

import com.mityushovn.miningquiz.database.topicDao.TopicDaoAPI
import com.mityushovn.miningquiz.models.Topic
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
class TopicsRepository(
    private val topicDao: TopicDaoAPI,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TopicsRepositoryAPI {

    /**
     * @see TopicsRepositoryAPI.getAllTopics
     */
    override suspend fun getAllTopics(): Flow<List<Topic>> {
        return topicDao.getAllTopics().flowOn(coroutineDispatcher)
    }
}