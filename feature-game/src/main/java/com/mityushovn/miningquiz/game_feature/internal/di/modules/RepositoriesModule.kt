package com.mityushovn.miningquiz.game_feature.internal.di.modules

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.core_domain.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.core_domain.domain.repositories.ExamsRepositoryAPI
import com.mityushovn.miningquiz.core_domain.domain.repositories.QuestionsRepositoryAPI
import com.mityushovn.miningquiz.core_domain.domain.repositories.TopicsRepositoryAPI
import com.mityushovn.miningquiz.data_attempts.api.factory.AttemptsRepositoryFactory
import com.mityushovn.miningquiz.data_exams.api.ExamsRepositoryFactory
import com.mityushovn.miningquiz.data_questions.api.QuestionsRepositoryFactory
import com.mityushovn.miningquiz.data_topics.api.TopicsRepositoryFactory
import dagger.Module
import dagger.Provides

@Module
internal object RepositoriesModule {

    @Provides
    fun provideQuestionsRepository(
        db: SQLiteDatabase
    ): QuestionsRepositoryAPI = QuestionsRepositoryFactory.createInstance(db)

    @Provides
    fun provideAttemptsRepository(
        db: SQLiteDatabase
    ): AttemptsRepositoryAPI = AttemptsRepositoryFactory.createInstance(db)

    @Provides
    fun provideExamsRepository(
        db: SQLiteDatabase
    ): ExamsRepositoryAPI = ExamsRepositoryFactory.createInstance(db)

    @Provides
    fun provideTopicsRepository(
        db: SQLiteDatabase
    ): TopicsRepositoryAPI = TopicsRepositoryFactory.createInstance(db)
}