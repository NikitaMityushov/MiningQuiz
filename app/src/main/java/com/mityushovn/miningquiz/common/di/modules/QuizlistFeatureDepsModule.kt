package com.mityushovn.miningquiz.common.di.modules

import com.mityushovn.miningquiz.common.di.components.AppComponent
import com.mityushovn.miningquiz.module_injector.annotations.DependenciesKey
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies
import com.mityushovn.quizlist_feature.api.QuizlistFeatureDependencies
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface QuizlistFeatureDepsModule {

    @Binds
    @IntoMap
    @DependenciesKey(QuizlistFeatureDependencies::class)
    fun bindQuizlistFeatureDependencies(impl: AppComponent): Dependencies
}