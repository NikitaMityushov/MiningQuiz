package com.mityushovn.miningquiz.game_feature.internal.di.modules

import android.app.Application
import com.mityushovn.miningquiz.core_domain.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.core_domain.domain.repositories.QuestionsRepositoryAPI
import com.mityushovn.miningquiz.core_domain.domain.repositories.SettingsRepositoryAPI
import com.mityushovn.miningquiz.game_feature.api.GameMode
import com.mityushovn.miningquiz.game_feature.internal.presentation.GameEngineFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
internal object GameEngineModule {

    @Provides
    fun provideGameEngineFactory(
        questionsRepository: QuestionsRepositoryAPI,
        attemptsRepository: AttemptsRepositoryAPI,
        settingsRepository: SettingsRepositoryAPI,
        mode: GameMode,
        examOrTopicId: Int,
        application: Application
    ) = GameEngineFactory(
        questionsRepository,
        attemptsRepository,
        settingsRepository,
        Dispatchers.IO,
        mode,
        examOrTopicId,
        application
    )
}