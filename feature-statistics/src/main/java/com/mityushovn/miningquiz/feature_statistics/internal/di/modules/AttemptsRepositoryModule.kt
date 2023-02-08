package com.mityushovn.miningquiz.feature_statistics.internal.di.modules

import com.mityushovn.miningquiz.core_domain.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.data_attempts.api.factory.AttemptsRepositoryFactory
import com.mityushovn.miningquiz.feature_statistics.api.StatisticsDeps
import dagger.Module
import dagger.Provides

@Module
internal object AttemptsRepositoryModule {

    @Provides
    fun provideAttemptsRepository(deps: StatisticsDeps): AttemptsRepositoryAPI {
        return AttemptsRepositoryFactory.createInstance(deps.db)
    }
}