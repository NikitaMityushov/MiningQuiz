package com.mityushovn.miningquiz.common.di.components

import android.app.Application
import com.mityushovn.miningquiz.common.MiningQuizApplication
import com.mityushovn.miningquiz.common.di.modules.*
import com.mityushovn.miningquiz.game_feature.api.GameFeatureDependencies
import com.mityushovn.miningquiz.main.presentation.activity.MainActivity
import com.mityushovn.miningquiz.main.presentation.mainfragment.MainFragment
import com.mityushovn.miningquiz.main.presentation.searchlistfragment.SearchListFragment
import com.mityushovn.miningquiz.main.presentation.searchlistfragment.questionfragment.QuestionFragment
import com.mityushovn.miningquiz.statistics_feature.api.StatisticsDeps
import com.mityushovn.quizlist_feature.api.QuizlistFeatureDependencies
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DatabaseModule::class,
        ViewModelModule::class,
        NavigationModule::class,
        RepositoryModule::class,
        QuizlistFeatureDepsModule::class,
        GameFeatureDepsModule::class,
        StatisticsDepsModule::class
    ]
)
interface AppComponent : QuizlistFeatureDependencies, GameFeatureDependencies, StatisticsDeps {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }

    fun injectIntoApplication(application: MiningQuizApplication)

    fun injectInMainActivity(activity: MainActivity)

    fun injectInMainFragment(fragment: MainFragment)

    fun injectInSearchListFragment(fragment: SearchListFragment)

    fun injectInQuestionFragment(fragment: QuestionFragment)
}