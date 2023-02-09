package com.mityushovn.miningquiz.feature_game.internal.di.components

import android.app.Application
import com.mityushovn.miningquiz.feature_game.api.GameFeatureDependencies
import com.mityushovn.miningquiz.feature_game.api.GameMode
import com.mityushovn.miningquiz.feature_game.api.QuizActivity
import com.mityushovn.miningquiz.feature_game.internal.di.modules.GameEngineModule
import com.mityushovn.miningquiz.feature_game.internal.di.modules.NavigationModule
import com.mityushovn.miningquiz.feature_game.internal.di.modules.RepositoriesModule
import com.mityushovn.miningquiz.feature_game.internal.presentation.congratsfragment.CongratsFragment
import com.mityushovn.miningquiz.feature_game.internal.presentation.failedfragment.FailedFragment
import com.mityushovn.miningquiz.feature_game.internal.presentation.gamefragment.GameFragment
import com.mityushovn.miningquiz.feature_game.internal.presentation.previewgamefragment.PreviewGameFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [GameFeatureDependencies::class],
    modules = [
        GameEngineModule::class,
        NavigationModule::class,
        RepositoriesModule::class
    ]
)
internal interface GameComponent {

    @Component.Factory
    interface Factory {
        fun create(
            deps: GameFeatureDependencies,
            @BindsInstance app: Application,
            @BindsInstance mode: GameMode,
            @BindsInstance examOrTopicId: Int
        ): GameComponent
    }

    fun inject(activity: QuizActivity)

    fun inject(fragment: PreviewGameFragment)

    fun inject(fragment: GameFragment)

    fun inject(fragment: CongratsFragment)

    fun inject(fragment: FailedFragment)
}