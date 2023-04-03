package com.mityushovn.miningquiz.di.modules

import com.mityushovn.miningquiz.di.components.AppComponent
import com.mityushovn.miningquiz.game_feature.api.GameFeatureDependencies
import com.mityushovn.miningquiz.module_injector.annotations.DependenciesKey
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface GameFeatureDepsModule {

    @Binds
    @IntoMap
    @DependenciesKey(GameFeatureDependencies::class)
    fun bindGameFeatureDependencies(impl: AppComponent): Dependencies
}