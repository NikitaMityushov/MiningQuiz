package com.mityushovn.miningquiz.feature_game.internal.di.modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mityushovn.miningquiz.feature_game.R
import com.mityushovn.miningquiz.feature_game.internal.navigation.QuizNavigator
import dagger.Module
import dagger.Provides

@Module
internal object NavigationModule {
    @Provides
    fun provideQuizNavigator() =
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