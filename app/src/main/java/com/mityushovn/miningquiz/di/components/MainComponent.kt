package com.mityushovn.miningquiz.di.components

import com.mityushovn.miningquiz.di.modules.MainActDaoModule
import com.mityushovn.miningquiz.di.modules.MainActRepositoryModule
import com.mityushovn.miningquiz.di.scopes.MainActivityScope
import com.mityushovn.miningquiz.screens.main.quizList.examsfragment.ExamsFragment
import com.mityushovn.miningquiz.screens.main.quizList.topicslistfragment.TopicsListFragment
import dagger.Subcomponent

@Subcomponent(modules = [MainActDaoModule::class, MainActRepositoryModule::class])
@MainActivityScope
interface MainComponent {

    fun injectInExamsFragment(examsFragment: ExamsFragment)

    fun injectInTopicsFragment(topicsListFragment: TopicsListFragment)
}