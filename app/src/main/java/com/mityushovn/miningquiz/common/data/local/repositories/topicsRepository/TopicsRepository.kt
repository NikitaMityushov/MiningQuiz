package com.mityushovn.miningquiz.common.data.local.repositories.topicsRepository

import com.mityushovn.miningquiz.common.data.local.database.topicDao.TopicDaoAPI
import com.mityushovn.miningquiz.common.di.qualifiers.RepositoryCoroutineDispatcher
import com.mityushovn.miningquiz.common.di.scopes.MainActivityScope
import com.mityushovn.miningquiz.common.domain.models.Topic
import com.mityushovn.miningquiz.common.domain.repositories.TopicsRepositoryAPI
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