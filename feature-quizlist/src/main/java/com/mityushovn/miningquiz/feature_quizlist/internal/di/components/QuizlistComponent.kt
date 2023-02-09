package com.mityushovn.miningquiz.feature_quizlist.internal.di.components

import com.mityushovn.miningquiz.feature_quizlist.api.QuizlistFeatureDependencies
import com.mityushovn.miningquiz.feature_quizlist.internal.di.modules.NavigationModule
import com.mityushovn.miningquiz.feature_quizlist.internal.di.modules.RepositoriesModule
import com.mityushovn.miningquiz.feature_quizlist.internal.di.modules.ViewModelsModule
import com.mityushovn.miningquiz.feature_quizlist.internal.presentation.examsfragment.ExamsFragment
import com.mityushovn.miningquiz.feature_quizlist.internal.presentation.topicslistfragment.TopicsListFragment
import dagger.Component

@Component(
    dependencies = [QuizlistFeatureDependencies::class],
    modules = [
        ViewModelsModule::class,
        NavigationModule::class,
        RepositoriesModule::class
    ]
)
internal interface QuizlistComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: QuizlistFeatureDependencies): QuizlistComponent
    }

    fun injectInExamsFragment(examsFragment: ExamsFragment)

    fun injectInTopicsListFragment(topicsListFragment: TopicsListFragment)
}