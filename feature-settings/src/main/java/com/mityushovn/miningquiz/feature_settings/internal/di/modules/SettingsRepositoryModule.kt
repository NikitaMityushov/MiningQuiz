package com.mityushovn.miningquiz.feature_settings.internal.di.modules

import com.mityushovn.mining_quiz.feature_settings.api.SettingsDeps
import com.mityushovn.miningquiz.core_domain.domain.repositories.SettingsRepositoryAPI
import com.mityushovn.miningquiz.data_settings.api.SettingsRepositoryFactory
import dagger.Module
import dagger.Provides

@Module
internal object SettingsRepositoryModule {
    @Provides
    fun provideSettingsRepository(deps: SettingsDeps): SettingsRepositoryAPI =
        SettingsRepositoryFactory.createInstance(deps.prefs)
}