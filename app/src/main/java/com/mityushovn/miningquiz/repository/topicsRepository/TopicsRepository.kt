package com.mityushovn.miningquiz.repository.topicsRepository

import com.mityushovn.miningquiz.database.topicDao.TopicDaoAPI
import com.mityushovn.miningquiz.di.qualifiers.RepositoryCoroutineDispatcher
import com.mityushovn.miningquiz.di.scopes.MainActivityScope
import com.mityushovn.miningquiz.models.domain.Topic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author Nikita Mityushov 12.04.22
 * @since 1.0
 * TopicsRepositoryAPI implementation.
 * @see TopicsRepositoryAPI
 */
@MainActivityScope
class TopicsRepository @Inject constructor(
    private val topicDao: TopicDaoAPI,
    @RepositoryCoroutineDispatcher
    private val coroutineDispatcher: CoroutineDispatcher
) : TopicsRepositoryAPI {

    /**
     * @see TopicsRepositoryAPI.getAllTopics
     */
    override suspend fun getAllTopics(): Flow<List<Topic>> {
        return topicDao.getAllTopics().flowOn(coroutineDispatcher)
    }
}