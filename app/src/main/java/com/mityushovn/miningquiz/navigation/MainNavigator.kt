package com.mityushovn.miningquiz.navigation

import android.view.View
import com.mityushovn.miningquiz.screens.main.mainfragment.MainFragment

/**
 * Navigator interface of MainFragment
 */
interface MainNavigator {
    fun onSearchViewIsFocused(fragment: MainFragment)
    fun onQuestionSelected(view: View, questionId: Int)
    fun onTopicSelected(topicId: Int)
    fun onExamSelected(examId: Int)
}