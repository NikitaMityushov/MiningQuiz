package com.mityushovn.mining_quiz.feature_settings.internal.di.modules

import com.mityushovn.mining_quiz.feature_settings.api.SettingsDeps
import com.mityushovn.miningquiz.core_domain.domain.repositories.SettingsRepositoryApi
import com.mityushovn.miningquiz.data_settings.api.SettingsRepositoryFactory
import dagger.Module
import dagger.Provides

@Module
internal object SettingsRepositoryModule {
    @Provides
    fun provideSettingsRepository(deps: SettingsDeps): SettingsRepositoryApi =
        SettingsRepositoryFactory.createInstance(deps.prefs)
}