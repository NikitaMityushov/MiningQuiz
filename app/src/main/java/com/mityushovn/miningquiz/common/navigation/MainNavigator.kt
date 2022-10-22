package com.mityushovn.miningquiz.common.navigation

import android.view.View
import com.mityushovn.miningquiz.main.presentation.mainfragment.MainFragment

/**
 * Navigator interface of MainFragment
 */
interface MainNavigator {
    fun onSearchViewIsFocused(fragment: MainFragment)
    fun onQuestionSelected(view: View, questionId: Int)
}