package com.mityushovn.miningquiz.feature_statistics.internal.di.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object DispatchersModule {

    @Provides
    fun providesBackgroundDispatcher(): CoroutineDispatcher = Dispatchers.Default
}