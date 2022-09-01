package com.mityushovn.miningquiz.common.di.components

import com.mityushovn.miningquiz.common.di.modules.MainActDaoModule
import com.mityushovn.miningquiz.common.di.modules.MainActRepositoryModule
import com.mityushovn.miningquiz.common.di.scopes.MainActivityScope
import com.mityushovn.miningquiz.main.presentation.quizList.examsfragment.ExamsFragment
import com.mityushovn.miningquiz.main.presentation.quizList.topicslistfragment.TopicsListFragment
import dagger.Subcomponent

@Subcomponent(modules = [MainActDaoModule::class, MainActRepositoryModule::class])
@MainActivityScope
interface MainComponent {

    fun injectInExamsFragment(examsFragment: ExamsFragment)

    fun injectInTopicsFragment(topicsListFragment: TopicsListFragment)
}