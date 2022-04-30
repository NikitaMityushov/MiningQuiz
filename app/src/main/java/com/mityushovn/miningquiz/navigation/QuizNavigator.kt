package com.mityushovn.miningquiz.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

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