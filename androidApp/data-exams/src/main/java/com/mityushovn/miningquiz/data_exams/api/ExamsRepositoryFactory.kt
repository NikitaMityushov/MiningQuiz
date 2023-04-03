package com.mityushovn.miningquiz.data_exams.api

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.core_domain.domain.repositories.ExamsRepositoryAPI
import com.mityushovn.miningquiz.data_exams.internal.examDao.ExamDao
import com.mityushovn.miningquiz.data_exams.internal.repositories.ExamsRepository

class ExamsRepositoryFactory {

    companion object {
        private var instance: ExamsRepositoryAPI? = null

        fun createInstance(db: SQLiteDatabase): ExamsRepositoryAPI {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = ExamsRepository(
                            examDao = ExamDao(db)
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