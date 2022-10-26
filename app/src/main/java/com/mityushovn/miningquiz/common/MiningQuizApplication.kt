package com.mityushovn.miningquiz.common

import android.app.Application
import com.mityushovn.miningquiz.common.di.components.AppComponent
import com.mityushovn.miningquiz.common.di.components.DaggerAppComponent
import com.mityushovn.miningquiz.module_injector.extensions.DepsMap
import com.mityushovn.miningquiz.module_injector.interfaces.DependenciesProvider
import timber.log.Timber
import javax.inject.Inject

class MiningQuizApplication : Application(), DependenciesProvider {

    lateinit var appComponent: AppComponent
        private set

    @Inject
    override lateinit var depsMap: DepsMap

    override fun onCreate() {
        super.onCreate()
        // 1) init Timber logging
        Timber.plant(Timber.DebugTree())
        // 2) init Dagger AppComponent
        /**
         * @see AppComponent
         */
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.injectIntoApplication(this)
    }
}