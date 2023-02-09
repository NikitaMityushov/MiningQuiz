package com.mityushovn.miningquiz.di.modules

import com.mityushovn.miningquiz.di.components.AppComponent
import com.mityushovn.miningquiz.module_injector.annotations.DependenciesKey
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies
import com.mityushovn.miningquiz.feature_statistics.api.StatisticsDeps
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface StatisticsDepsModule {

    @Binds
    @IntoMap
    @DependenciesKey(StatisticsDeps::class)
    fun bindStatisticsDeps(impl: AppComponent): Dependencies
}