package com.mityushovn.miningquiz.common.di.modules

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.main.presentation.activity.MainActivity
import com.mityushovn.miningquiz.quiz.presentation.quiz.GameEngine
import com.mityushovn.miningquiz.quiz.presentation.quiz.QuizActivity
import com.mityushovn.miningquiz.common.navigation.MainNavigator
import com.mityushovn.miningquiz.common.navigation.QuizNavigator
import com.mityushovn.miningquiz.main.presentation.mainfragment.MainFragment
import com.mityushovn.miningquiz.main.presentation.searchlistfragment.questionfragment.QuestionFragment
import dagger.Module
import dagger.Provides
import timber.log.Timber

private const val EXAM_OR_TOPIC_ID = "exam_or_topic_id"
private const val GAME_MODE = "game_mode"

@Module
class NavigationModule {

    @Provides
    fun provideMainNavigator(): MainNavigator =
        object : MainNavigator {
            override fun onSearchViewIsFocused(fragment: MainFragment) {
                fragment.childFragmentManager.fragments[0]?.findNavController()
                    ?.navigate(R.id.action_global_searchListFragment2)
            }

            override fun onQuestionSelected(view: View, questionId: Int) {
                Timber.d("Navigation.onQuestionsSelected, questionId = $questionId")
                view.findNavController()
                    .navigate(R.id.action_searchListFragment2_to_questionFragment, Bundle().apply {
                        putInt(QuestionFragment.ARG_QUESTION_ID, questionId)
                    })
            }

            override fun onTopicSelected(topicId: Int, activity: MainActivity) {
                Timber.d("OnTopicSelected, topicId = $topicId")
                val intent = Intent(activity, QuizActivity::class.java)
                intent.apply {
                    putExtra(EXAM_OR_TOPIC_ID, topicId)
                    putExtra(GAME_MODE, GameEngine.GameMode.TOPIC)
                }
                activity.startActivity(intent)
            }

            override fun onExamSelected(examId: Int, activity: MainActivity) {
                Timber.d("OnExamSelected, examId = $examId")
                val intent = Intent(activity, QuizActivity::class.java)
                intent.apply {
                    putExtra(EXAM_OR_TOPIC_ID, examId)
                    putExtra(GAME_MODE, GameEngine.GameMode.EXAM)
                }
                activity.startActivity(intent)
            }
        }

    @Provides
    fun provideQuizNavigator(): QuizNavigator =
        object : QuizNavigator {
            override fun toGameFragment(fragment: Fragment, bundle: Bundle?) {
                fragment.findNavController()
                    .navigate(R.id.action_previewGameFragment_to_gameFragment, bundle)
            }

            override fun toCongratsFragment(fragment: Fragment) {
                fragment.findNavController().navigate(R.id.action_gameFragment_to_congratsFragment)
            }

            override fun toFailedFragment(fragment: Fragment) {
                fragment.findNavController().navigate(R.id.action_gameFragment_to_failedFragment)
            }

            override fun quitTestFromGameFr(fragment: Fragment) {
                fragment.findNavController().navigate(R.id.action_gameFragment_to_mainActivity)
            }

            override fun quitTestFromCongratsFr(fragment: Fragment) {
                fragment.findNavController().navigate(R.id.action_congratsFragment_to_mainActivity)
            }

            override fun quitTestFromFailedFr(fragment: Fragment) {
                fragment.findNavController()
                    .navigate(R.id.action_failedFragment_to_mainActivity)
            }

            override fun popStack(fragment: Fragment) {
                fragment.findNavController().popBackStack()
            }
        }
}