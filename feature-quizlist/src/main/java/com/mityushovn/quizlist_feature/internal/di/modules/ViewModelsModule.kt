package com.mityushovn.quizlist_feature.internal.di.modules

import com.mityushovn.miningquiz.core_domain.domain.repositories.ExamsRepositoryAPI
import com.mityushovn.miningquiz.core_domain.domain.repositories.TopicsRepositoryAPI
import com.mityushovn.quizlist_feature.internal.presentation.examsfragment.ExamsVMFactory
import com.mityushovn.quizlist_feature.internal.presentation.topicslistfragment.TopicsListVMFactory
import dagger.Module
import dagger.Provides

@Module
internal object ViewModelsModule {

    @Provides
    fun provideExamsVMFactory(repository: ExamsRepositoryAPI) = ExamsVMFactory(repository)

    @Provides
    fun provideTopicsVMFactory(repository: TopicsRepositoryAPI) = TopicsListVMFactory(repository)
}