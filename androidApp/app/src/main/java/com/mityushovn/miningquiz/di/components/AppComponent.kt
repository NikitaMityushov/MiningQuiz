package com.mityushovn.miningquiz.di.components

import android.app.Application
import android.content.SharedPreferences
import com.mityushovn.mining_quiz.feature_settings.api.SettingsDeps
import com.mityushovn.miningquiz.MiningQuizApplication
import com.mityushovn.miningquiz.di.modules.*
import com.mityushovn.miningquiz.game_feature.api.GameFeatureDependencies
import com.mityushovn.miningquiz.main.presentation.activity.MainActivity
import com.mityushovn.miningquiz.main.presentation.mainfragment.MainFragment
import com.mityushovn.miningquiz.main.presentation.searchlistfragment.SearchListFragment
import com.mityushovn.miningquiz.main.presentation.searchlistfragment.questionfragment.QuestionFragment
import com.mityushovn.miningquiz.statistics_feature.api.StatisticsDeps
import com.mityushovn.miningquiz.quizlist_feature.api.QuizlistFeatureDependencies
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
        StatisticsDepsModule::class,
        SettingsFeatureDepsModule::class
    ]
)
interface AppComponent : QuizlistFeatureDependencies, GameFeatureDependencies, StatisticsDeps,
    SettingsDeps {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance prefs: SharedPreferences,
            @BindsInstance application: Application
        ): AppComponent
    }

    fun injectIntoApplication(application: MiningQuizApplication)

    fun injectInMainActivity(activity: MainActivity)

    fun injectInMainFragment(fragment: MainFragment)

    fun injectInSearchListFragment(fragment: SearchListFragment)

    fun injectInQuestionFragment(fragment: QuestionFragment)
}