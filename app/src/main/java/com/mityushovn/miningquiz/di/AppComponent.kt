package com.mityushovn.miningquiz.di

import android.app.Application
import android.content.Context
import com.mityushovn.miningquiz.activities.main.MainActivity
import com.mityushovn.miningquiz.activities.quiz.QuizActivity
import com.mityushovn.miningquiz.di.modules.*
import com.mityushovn.miningquiz.screens.main.mainfragment.MainFragment
import com.mityushovn.miningquiz.screens.main.quizList.examsfragment.ExamsFragment
import com.mityushovn.miningquiz.screens.main.quizList.topicslistfragment.TopicsListFragment
import com.mityushovn.miningquiz.screens.main.searchlistfragment.SearchListFragment
import com.mityushovn.miningquiz.screens.main.searchlistfragment.questionfragment.QuestionFragment
import com.mityushovn.miningquiz.screens.main.statisticsfragment.StatisticsFragment
import com.mityushovn.miningquiz.screens.quiz.congratsfragment.CongratsFragment
import com.mityushovn.miningquiz.screens.quiz.failedfragment.FailedFragment
import com.mityushovn.miningquiz.screens.quiz.gamefragment.GameFragment
import com.mityushovn.miningquiz.screens.quiz.previewgamefragment.PreviewGameFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [DatabaseModule::class,
        NavigationModule::class,
        RepositoryModule::class,
        DaoModule::class,
        CoroutinesDispatchersModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
            @BindsInstance application: Application
        ): AppComponent
    }

    fun injectInExamsFragment(examsFragment: ExamsFragment)

    fun injectInTopicsFragment(topicsListFragment: TopicsListFragment)

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