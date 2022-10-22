package com.mityushovn.miningquiz.game_feature.internal.di.modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mityushovn.miningquiz.game_feature.internal.navigation.QuizNavigator
import dagger.Module
import dagger.Provides

@Module
internal object NavigationModule {
    @Provides
    fun provideQuizNavigator() =
        object : QuizNavigator {
            override fun toGameFragment(fragment: Fragment, bundle: Bundle?) {
                TODO("Not yet implemented")
            }

            override fun toCongratsFragment(fragment: Fragment) {
                TODO("Not yet implemented")
            }

            override fun toFailedFragment(fragment: Fragment) {
                TODO("Not yet implemented")
            }

            override fun quitTestFromGameFr(fragment: Fragment) {
                TODO("Not yet implemented")
            }

            override fun quitTestFromCongratsFr(fragment: Fragment) {
                TODO("Not yet implemented")
            }

            override fun quitTestFromFailedFr(fragment: Fragment) {
                TODO("Not yet implemented")
            }

            override fun popStack(fragment: Fragment) {
                TODO("Not yet implemented")
            }
        }

}