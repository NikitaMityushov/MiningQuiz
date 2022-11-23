package com.mityushovn.miningquiz

import android.app.Application
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mityushovn.miningquiz.di.components.AppComponent
import com.mityushovn.miningquiz.di.components.DaggerAppComponent
import com.mityushovn.miningquiz.module_injector.extensions.DepsMap
import com.mityushovn.miningquiz.module_injector.interfaces.DependenciesProvider
import com.mityushovn.miningquiz.work.ReminderWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val REMINDER_REPEAT_INTERVAL = 7L

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
        // 3) create reminder work
        createAndConfigureWork()
    }

    private fun createAndConfigureWork() {
        // 1) create constraints
        val workManager = WorkManager.getInstance(this)

        val constraints = Constraints.Builder()
            .build()

        val reminderWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            REMINDER_REPEAT_INTERVAL, TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueue(reminderWorkRequest)
    }
}