package com.mityushovn.miningquiz.common.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Navigator of QuizActivity
 */
interface QuizNavigator {
    fun toGameFragment(fragment: Fragment, bundle: Bundle?)
    fun toCongratsFragment(fragment: Fragment)
    fun toFailedFragment(fragment: Fragment)
    fun quitTestFromGameFr(fragment: Fragment)
    fun quitTestFromCongratsFr(fragment: Fragment)
    fun quitTestFromFailedFr(fragment: Fragment)
    fun popStack(fragment: Fragment)
}