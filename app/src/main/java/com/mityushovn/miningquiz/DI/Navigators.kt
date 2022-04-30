package com.mityushovn.miningquiz.DI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.activities.main.MainActivity
import com.mityushovn.miningquiz.activities.quiz.GameEngine
import com.mityushovn.miningquiz.activities.quiz.QuizActivity
import com.mityushovn.miningquiz.navigation.MainNavigator
import com.mityushovn.miningquiz.navigation.QuizNavigator
import com.mityushovn.miningquiz.screens.main.mainfragment.MainFragment
import com.mityushovn.miningquiz.screens.main.searchlistfragment.questionfragment.QuestionFragment
import timber.log.Timber

private const val EXAM_OR_TOPIC_ID = "exam_or_topic_id"
private const val GAME_MODE = "game_mode"

/**
 * DI for all Navigation
 * @see MainNavigator
 * @see QuizNavigator
 */
object Navigators {
    private lateinit var applicationContext: Context

    val mainNavigator: MainNavigator by lazy {
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
    }

    val quizNavigator: QuizNavigator by lazy {
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
                fragment.findNavController().navigate(R.id.action_failedFragment_to_mainActivity)
            }

            override fun popStack(fragment: Fragment) {
                fragment.findNavController().popBackStack()
            }

        }
    }

    // This method should be called in onCreate() of Application, or on Activity, or Service
    // for initialization database and repositories
    fun init(context: Context) {
        applicationContext = context
    }
}