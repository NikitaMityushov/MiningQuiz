package com.mityushovn.quizlist_feature.internal.di.components

import com.mityushovn.quizlist_feature.api.QuizlistFeatureDependencies
import com.mityushovn.quizlist_feature.internal.di.modules.NavigationModule
import com.mityushovn.quizlist_feature.internal.di.modules.RepositoriesModule
import com.mityushovn.quizlist_feature.internal.di.modules.ViewModelsModule
import com.mityushovn.quizlist_feature.internal.presentation.examsfragment.ExamsFragment
import com.mityushovn.quizlist_feature.internal.presentation.topicslistfragment.TopicsListFragment
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