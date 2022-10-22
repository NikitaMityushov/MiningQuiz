package com.mityushovn.miningquiz.common.di.modules

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.core_domain.domain.repositories.QuestionsRepositoryAPI
import com.mityushovn.miningquiz.data_questions.api.QuestionsRepositoryFactory
import dagger.Module
import dagger.Provides

@Module
object RepositoryModule {

    @Provides
    fun provideQuestionsRepository(db: SQLiteDatabase): QuestionsRepositoryAPI {
        return QuestionsRepositoryFactory.createInstance(db)
    }
}