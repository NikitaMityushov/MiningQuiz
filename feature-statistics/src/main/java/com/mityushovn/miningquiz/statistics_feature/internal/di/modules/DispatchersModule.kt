package com.mityushovn.miningquiz.statistics_feature.internal.di.modules

import com.mityushovn.miningquiz.core_di.qualifiers.ViewModelBackgroundCoroutineDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object DispatchersModule {

    @Provides
    @ViewModelBackgroundCoroutineDispatcher
    fun providesBackgroundDispatcher(): CoroutineDispatcher = Dispatchers.Default
}