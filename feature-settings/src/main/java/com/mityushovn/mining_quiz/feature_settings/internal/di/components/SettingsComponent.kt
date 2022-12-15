package com.mityushovn.mining_quiz.feature_settings.internal.di.components

import com.mityushovn.mining_quiz.feature_settings.api.SettingsDeps
import com.mityushovn.mining_quiz.feature_settings.api.SettingsFragment
import com.mityushovn.mining_quiz.feature_settings.internal.di.modules.SettingsRepositoryModule
import dagger.Component

@Component(
    dependencies = [SettingsDeps::class],
    modules = [SettingsRepositoryModule::class]
)
internal interface SettingsComponent {

    fun inject(settingsFragment: SettingsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            deps: SettingsDeps
        ): SettingsComponent
    }
}