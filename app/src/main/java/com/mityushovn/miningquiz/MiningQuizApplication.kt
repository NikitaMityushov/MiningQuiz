package com.mityushovn.miningquiz

import android.app.Application
import com.mityushovn.miningquiz.DI.Navigators
import com.mityushovn.miningquiz.DI.Repositories
import timber.log.Timber

class MiningQuizApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 1) init Timber logging
        Timber.plant(Timber.DebugTree())
        // 2) init repositories and database
        Repositories.init(applicationContext)
        // 3) init Navigators
        Navigators.init(applicationContext)
    }
}