package com.mityushovn.miningquiz.feature_quizlist.internal.di.modules

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.core_domain.domain.repositories.ExamsRepositoryAPI
import com.mityushovn.miningquiz.core_domain.domain.repositories.TopicsRepositoryAPI
import com.mityushovn.miningquiz.data_exams.api.ExamsRepositoryFactory
import com.mityushovn.miningquiz.data_topics.api.TopicsRepositoryFactory
import dagger.Module
import dagger.Provides

@Module
internal object RepositoriesModule {
    @Provides
    fun provideExamsRepository(
        db: SQLiteDatabase
    ): ExamsRepositoryAPI = ExamsRepositoryFactory.createInstance(db)

    @Provides
    fun provideTopicsRepository(
        db: SQLiteDatabase
    ): TopicsRepositoryAPI = TopicsRepositoryFactory.createInstance(db)

}