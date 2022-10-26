package com.mityushovn.miningquiz.common.di.modules

import com.mityushovn.miningquiz.common.di.components.AppComponent
import com.mityushovn.miningquiz.module_injector.annotations.DependenciesKey
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies
import com.mityushovn.miningquiz.statistics_feature.api.StatisticsDeps
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