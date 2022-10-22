package com.mityushovn.miningquiz.game_feature.internal.di.components

import android.app.Application
import com.mityushovn.miningquiz.game_feature.api.GameFeatureDependencies
import com.mityushovn.miningquiz.game_feature.internal.di.modules.GameEngineModule
import com.mityushovn.miningquiz.game_feature.internal.di.modules.NavigationModule
import com.mityushovn.miningquiz.game_feature.internal.di.modules.RepositoriesModule
import com.mityushovn.miningquiz.game_feature.internal.presentation.congratsfragment.CongratsFragment
import com.mityushovn.miningquiz.game_feature.internal.presentation.failedfragment.FailedFragment
import com.mityushovn.miningquiz.game_feature.internal.presentation.gamefragment.GameFragment
import com.mityushovn.miningquiz.game_feature.internal.presentation.previewgamefragment.PreviewGameFragment
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
            @BindsInstance app: Application
        ): GameComponent
    }

    fun inject(fragment: PreviewGameFragment)

    fun inject(fragment: GameFragment)

    fun inject(fragment: CongratsFragment)

    fun inject(fragment: FailedFragment)
}