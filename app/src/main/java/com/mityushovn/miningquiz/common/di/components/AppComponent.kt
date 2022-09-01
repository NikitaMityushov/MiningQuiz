package com.mityushovn.miningquiz.common.di.components

import android.app.Application
import android.content.Context
import com.mityushovn.miningquiz.main.presentation.activity.MainActivity
import com.mityushovn.miningquiz.quiz.presentation.quiz.QuizActivity
import com.mityushovn.miningquiz.common.di.modules.*
import com.mityushovn.miningquiz.common.di.scopes.AppScope
import com.mityushovn.miningquiz.main.presentation.mainfragment.MainFragment
import com.mityushovn.miningquiz.main.presentation.searchlistfragment.SearchListFragment
import com.mityushovn.miningquiz.main.presentation.searchlistfragment.questionfragment.QuestionFragment
import com.mityushovn.miningquiz.main.presentation.statisticsfragment.StatisticsFragment
import com.mityushovn.miningquiz.quiz.presentation.congratsfragment.CongratsFragment
import com.mityushovn.miningquiz.quiz.presentation.failedfragment.FailedFragment
import com.mityushovn.miningquiz.quiz.presentation.gamefragment.GameFragment
import com.mityushovn.miningquiz.quiz.presentation.previewgamefragment.PreviewGameFragment
import dagger.BindsInstance
import dagger.Component

/**
 * Represents DI graph for MiningQuiz Application.
 */
@Component(
    modules = [DatabaseModule::class,
        NavigationModule::class,
        AppRepositoryModule::class,
        AppDaoModule::class,
        CoroutinesDispatchersModule::class]
)
@AppScope
interface AppComponent {

    fun mainComponent(): MainComponent

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
            @BindsInstance application: Application
        ): AppComponent
    }

//    fun injectInExamsFragment(examsFragment: ExamsFragment)
//
//    fun injectInTopicsFragment(topicsListFragment: TopicsListFragment)

    fun injectInStatisticsFragment(statisticsFragment: StatisticsFragment)

    fun injectInGameFragment(gameFragment: GameFragment)

    fun injectInQuizActivity(activity: QuizActivity)

    fun injectInFailedFragment(failedFragment: FailedFragment)

    fun injectInCongratsFragment(congratsFragment: CongratsFragment)

    fun injectInPreviewGameFragment(previewGameFragment: PreviewGameFragment)

    fun injectInSearchListFragment(searchListFragment: SearchListFragment)

    fun injectInMainFragment(mainFragment: MainFragment)

    fun injectInMainActivity(mainActivity: MainActivity)

    fun injectInQuestionFragment(questionFragment: QuestionFragment)
}