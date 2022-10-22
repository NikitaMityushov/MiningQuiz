package com.mityushovn.miningquiz.data_questions.api

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.core_domain.domain.repositories.QuestionsRepositoryAPI
import com.mityushovn.miningquiz.data_questions.internal.repositories.QuestionsRepository
import com.mityushovn.miningquiz.data_questions.internal.questionsDao.QuestionsDao

class QuestionsRepositoryFactory {

    companion object {
        private var instance: QuestionsRepositoryAPI? = null

        fun createInstance(db: SQLiteDatabase): QuestionsRepositoryAPI {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = QuestionsRepository(
                            questionsDao = QuestionsDao(db)
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