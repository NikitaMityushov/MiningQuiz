package com.mityushovn.miningquiz

import android.app.Application
import com.mityushovn.miningquiz.di.components.AppComponent
import com.mityushovn.miningquiz.di.components.DaggerAppComponent
import timber.log.Timber

class MiningQuizApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        // 1) init Timber logging
        Timber.plant(Timber.DebugTree())
        // 2) init Dagger AppComponent
        /**
         * @see AppComponent
         */
        appComponent = DaggerAppComponent.factory().create(applicationContext, this)
    }
}