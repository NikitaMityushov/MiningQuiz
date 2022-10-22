package com.mityushovn.miningquiz.common.di.modules

import android.view.View
import com.mityushovn.miningquiz.common.navigation.MainNavigator
import com.mityushovn.miningquiz.main.presentation.mainfragment.MainFragment
import dagger.Module
import dagger.Provides

@Module
object NavigationModule {

    @Provides
    fun provideMainNavigator(): MainNavigator {
        return object : MainNavigator {
            override fun onSearchViewIsFocused(fragment: MainFragment) {
                TODO("Not yet implemented")
            }

            override fun onQuestionSelected(view: View, questionId: Int) {
                TODO("Not yet implemented")
            }
        }
    }
}