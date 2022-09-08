package com.mityushovn.miningquiz.common.di.modules

import com.mityushovn.miningquiz.common.di.qualifiers.RepositoryCoroutineDispatcher
import com.mityushovn.miningquiz.common.di.qualifiers.ViewModelBackgroundCoroutineDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object CoroutinesDispatchersModule {

    @Provides
    @RepositoryCoroutineDispatcher
    fun provideRepositoryCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @ViewModelBackgroundCoroutineDispatcher
    fun provideViewModelBackgroundCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default
}