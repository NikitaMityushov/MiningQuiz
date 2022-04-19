package com.mityushovn.miningquiz.DI

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.navigation.MainNavigator
import com.mityushovn.miningquiz.screens.main.mainfragment.MainFragment
import com.mityushovn.miningquiz.screens.main.searchlistfragment.questionfragment.QuestionFragment
import timber.log.Timber

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

            override fun onTopicSelected(topicId: Int) {
                // TODO: 19.04.2022
                Timber.d("OnTopicSelected, topicId = $topicId")
            }

            override fun onExamSelected(examId: Int) {
                // TODO: 19.04.2022
                Timber.d("OnExamSelected, examId = $examId")
            }

        }
    }

    // This method should be called in onCreate() of Application, or on Activity, or Service
    // for initialization database and repositories
    fun init(context: Context) {
        applicationContext = context
    }
}