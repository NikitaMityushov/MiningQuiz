package com.mityushovn.miningquiz.statistics_feature.internal.di.modules

import com.mityushovn.miningquiz.core_domain.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.data_attempts.api.factory.AttemptsRepositoryFactory
import com.mityushovn.miningquiz.statistics_feature.api.StatisticsDeps
import dagger.Module
import dagger.Provides

@Module
internal object AttemptsRepositoryModule {

    @Provides
    fun provideAttemptsRepository(deps: StatisticsDeps): AttemptsRepositoryAPI {
        return AttemptsRepositoryFactory.createInstance(deps.db)
    }
}