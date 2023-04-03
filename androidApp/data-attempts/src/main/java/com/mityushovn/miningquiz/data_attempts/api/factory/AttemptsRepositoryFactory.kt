package com.mityushovn.miningquiz.data_attempts.api.factory

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.core_domain.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.data_attempts.internal.attemptExamDao.AttemptExamDao
import com.mityushovn.miningquiz.data_attempts.internal.attemptTopicDao.AttemptTopicDao
import com.mityushovn.miningquiz.data_attempts.internal.repositories.AttemptsRepository

class AttemptsRepositoryFactory {
    companion object {
        private var instance: AttemptsRepositoryAPI? = null

        fun createInstance(db: SQLiteDatabase): AttemptsRepositoryAPI {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = AttemptsRepository(
                            attemptExamDao = AttemptExamDao(db),
                            attemptTopicDao = AttemptTopicDao(db)
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