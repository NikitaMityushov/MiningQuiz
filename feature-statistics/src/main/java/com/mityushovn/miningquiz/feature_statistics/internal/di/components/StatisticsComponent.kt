package com.mityushovn.miningquiz.feature_statistics.internal.di.components

import android.app.Application
import com.mityushovn.miningquiz.feature_statistics.api.StatisticsDeps
import com.mityushovn.miningquiz.feature_statistics.api.StatisticsFragment
import com.mityushovn.miningquiz.feature_statistics.internal.di.modules.AttemptsRepositoryModule
import com.mityushovn.miningquiz.feature_statistics.internal.di.modules.DispatchersModule
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