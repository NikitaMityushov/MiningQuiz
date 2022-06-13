package com.mityushovn.miningquiz

import android.app.Application
import com.mityushovn.miningquiz.di.AppComponent
import com.mityushovn.miningquiz.di.DaggerAppComponent
import com.mityushovn.miningquiz.di.Navigators
import timber.log.Timber

class MiningQuizApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        // 1) init Timber logging
        Timber.plant(Timber.DebugTree())
        // 2) init repositories and database
        appComponent = DaggerAppComponent.factory().create(applicationContext, this)
        // 3) init Navigators
        Navigators.init(applicationContext)
    }
}