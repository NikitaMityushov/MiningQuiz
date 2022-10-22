package com.mityushovn.miningquiz.common.di.components

import android.app.Application
import com.mityushovn.miningquiz.common.di.modules.DatabaseModule
import com.mityushovn.miningquiz.common.di.modules.NavigationModule
import com.mityushovn.miningquiz.common.di.modules.RepositoryModule
import com.mityushovn.miningquiz.common.di.modules.ViewModelModule
import com.mityushovn.miningquiz.main.presentation.activity.MainActivity
import com.mityushovn.miningquiz.main.presentation.mainfragment.MainFragment
import com.mityushovn.miningquiz.main.presentation.searchlistfragment.SearchListFragment
import com.mityushovn.miningquiz.main.presentation.searchlistfragment.questionfragment.QuestionFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DatabaseModule::class,
        ViewModelModule::class,
        NavigationModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }

    fun injectInMainActivity(activity: MainActivity)

    fun injectInMainFragment(fragment: MainFragment)

    fun injectInSearchListFragment(fragment: SearchListFragment)

    fun injectInQuestionFragment(fragment: QuestionFragment)
}