package com.mityushovn.miningquiz.di.modules

import com.mityushovn.mining_quiz.feature_settings.api.SettingsDeps
import com.mityushovn.miningquiz.di.components.AppComponent
import com.mityushovn.miningquiz.module_injector.annotations.DependenciesKey
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SettingsFeatureDepsModule {

    @Binds
    @IntoMap
    @DependenciesKey(SettingsDeps::class)
    fun bindSettingsDeps(impl: AppComponent): Dependencies
}