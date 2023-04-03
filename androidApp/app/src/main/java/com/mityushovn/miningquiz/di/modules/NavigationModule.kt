package com.mityushovn.miningquiz.di.modules

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.navigation.MainNavigator
import com.mityushovn.miningquiz.main.presentation.mainfragment.MainFragment
import com.mityushovn.miningquiz.main.presentation.searchlistfragment.questionfragment.QuestionFragment
import dagger.Module
import dagger.Provides
import timber.log.Timber

@Module
object NavigationModule {

    @Provides
    fun provideMainNavigator(): MainNavigator {
        return object : MainNavigator {
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
        }
    }
}