package com.mityushovn.miningquiz.common.navigation

import android.view.View
import com.mityushovn.miningquiz.main.presentation.activity.MainActivity
import com.mityushovn.miningquiz.main.presentation.mainfragment.MainFragment

/**
 * Navigator interface of MainFragment
 */
interface MainNavigator {
    fun onSearchViewIsFocused(fragment: MainFragment)
    fun onQuestionSelected(view: View, questionId: Int)
    fun onTopicSelected(topicId: Int, activity: MainActivity)
    fun onExamSelected(examId: Int, activity: MainActivity)
}