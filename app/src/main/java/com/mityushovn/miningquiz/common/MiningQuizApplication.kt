package com.mityushovn.miningquiz.common

import android.app.Application
import com.mityushovn.miningquiz.common.di.components.AppComponent
import com.mityushovn.miningquiz.common.di.components.DaggerAppComponent
import com.mityushovn.miningquiz.module_injector.extensions.DepsMap
import com.mityushovn.miningquiz.module_injector.interfaces.DependenciesProvider
import timber.log.Timber

class MiningQuizApplication : Application(), DependenciesProvider {

    lateinit var appComponent: AppComponent
        private set

    override val depsMap: DepsMap
        get() = TODO("Not yet implemented")

    override fun onCreate() {
        super.onCreate()
        // 1) init Timber logging
        Timber.plant(Timber.DebugTree())
        // 2) init Dagger AppComponent
        /**
         * @see AppComponent
         */
        appComponent = DaggerAppComponent.factory().create(this)
    }
}