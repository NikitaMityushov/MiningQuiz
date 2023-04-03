package com.mityushovn.miningquiz.game_feature.internal.di.modules

import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.core_domain.domain.repositories.*
import com.mityushovn.miningquiz.data_attempts.api.factory.AttemptsRepositoryFactory
import com.mityushovn.miningquiz.data_exams.api.ExamsRepositoryFactory
import com.mityushovn.miningquiz.data_questions.api.QuestionsRepositoryFactory
import com.mityushovn.miningquiz.data_settings.api.SettingsRepositoryFactory
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

    @Provides
    fun provideSettingsRepository(
        prefs: SharedPreferences
    ): SettingsRepositoryAPI = SettingsRepositoryFactory.createInstance(prefs)
}