package com.mityushovn.miningquiz.statistics_feature.internal.di.components

import android.app.Application
import com.mityushovn.miningquiz.statistics_feature.api.StatisticsDeps
import com.mityushovn.miningquiz.statistics_feature.api.StatisticsFragment
import com.mityushovn.miningquiz.statistics_feature.internal.di.modules.AttemptsRepositoryModule
import com.mityushovn.miningquiz.statistics_feature.internal.di.modules.DispatchersModule
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [StatisticsDeps::class],
    modules = [AttemptsRepositoryModule::class, DispatchersModule::class]
)
internal interface StatisticsComponent {

    fun inject(statisticsFragment: StatisticsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            deps: StatisticsDeps,
            @BindsInstance application: Application
        ): StatisticsComponent
    }
}