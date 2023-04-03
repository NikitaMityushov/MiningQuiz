package com.mityushovn.miningquiz.data_topics.api

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.core_domain.domain.repositories.TopicsRepositoryAPI
import com.mityushovn.miningquiz.data_topics.internal.repositories.TopicsRepository
import com.mityushovn.miningquiz.data_topics.internal.topicDao.TopicDao

class TopicsRepositoryFactory {
    companion object {
        private var instance: TopicsRepositoryAPI? = null

        fun createInstance(db: SQLiteDatabase): TopicsRepositoryAPI {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = TopicsRepository(
                            TopicDao(db)
                        )

                    }
                }
            }
            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }
    }
}